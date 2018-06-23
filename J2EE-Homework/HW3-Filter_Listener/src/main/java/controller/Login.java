package controller;

import util.CounterDisplay;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author hiki on 2017-12-19
 */

@WebServlet("/login")
public class Login extends HttpServlet{

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        // auto fill the inputs
        HttpSession session = request.getSession();
//        session.setMaxInactiveInterval(10);

        // get login id from cookie
        String login = "";
        Cookie[] cookies = request.getCookies();
        if (null != cookies){
            for (Cookie cookie : cookies) {
                if ("login".equals(cookie.getName())) {
                    login = cookie.getValue();
                    break;
                }
            }
        }

        // logout action removes the session, but cookie remains
        if (null != request.getParameter("logout")) {
            if (null != session) {
                session.removeAttribute("login");
                session.removeAttribute("password");
                session.removeAttribute("maxPageNum");
            }
        }


        PrintWriter out = response.getWriter();
        // write the return html file
        out.println("<!DOCTYPE html>\n" +
                    "<html lang='en'>\n" +
                    "<head>\n" +
                    "    <meta charset='UTF-8'>\n" +
                    "    <title>登录</title>\n" +
                    "</head>\n" +
                    "<body>\n");
        out.println("<h1>客户登录</h1>\n" +
                    "<form action='" + response.encodeURL(request.getContextPath() + "/orderList") + "' method='post'>\n" +
                    "    账号：<input type='text' name='id' value='" + login + "'/>\n" +
                    "    <br/>\n" +
                    "    密码：<input type='password' name='password'/>\n" +
                    "    <input type='submit' value='登录'/>\n" +
                    "</form>\n");
        CounterDisplay.displayUserCounter(request, response);
        out.println("</body></html>");
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) {

    }



}
