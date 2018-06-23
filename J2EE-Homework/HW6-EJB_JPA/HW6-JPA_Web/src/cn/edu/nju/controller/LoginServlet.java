package cn.edu.nju.controller;

import cn.edu.nju.factory.ServiceFactory;
import cn.edu.nju.service.CustomerService;
import cn.edu.nju.util.CookieHelper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

/**
 * @author hiki on 2017-12-19
 */

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        // auto fill the inputs
        HttpSession session = request.getSession();

        // get login id from cookie
        String cookieLogin = CookieHelper.getValueInCookies(request.getCookies(), "cookieLogin");
        if (cookieLogin == null) {
            cookieLogin = "";
        }

        // transfer to jsp
        request.setAttribute("cookieLogin", cookieLogin);

        // logout action removes the session, but cookie remains
        if (request.getParameter("logout") != null) {
            if (session != null) {
                session.removeAttribute("login");
                session.removeAttribute("password");
                session.removeAttribute("maxPageNum");
            }
        }

        request.getRequestDispatcher("/jsp/loginPage.jsp")
                .forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        // get the parameter of login
        int loginId = Integer.valueOf(request.getParameter("id"));
        String loginPassword = request.getParameter("password");

        // change the cookie
        Cookie cookie = CookieHelper.findOne(request.getCookies(), "cookieLogin");
        if (cookie != null) {
            if (!String.valueOf(loginId).equals(cookie.getValue())) {
                cookie.setValue(String.valueOf(loginId));
                response.addCookie(cookie);
            }
        } else {
            cookie = new Cookie("cookieLogin", String.valueOf(loginId));
            cookie.setMaxAge(Integer.MAX_VALUE);
            response.addCookie(cookie);
        }

        // remove session
        HttpSession session = request.getSession();
        session.removeAttribute("login");
        session.removeAttribute("password");
        session.removeAttribute("maxPageNum");

        // check if logs in successfully
        CustomerService cs = ServiceFactory.getCustomerService();
        if (!cs.login(loginId, loginPassword)) {
            session.setAttribute("loginError", true);
            response.sendRedirect("/login");
        }
        else {
            // change session attributes
            session.removeAttribute("loginError");
            session.setAttribute("login", loginId);
            session.setAttribute("password", loginPassword);
            request.getRequestDispatcher("/orderList").forward(request, response);
        }


    }

}
