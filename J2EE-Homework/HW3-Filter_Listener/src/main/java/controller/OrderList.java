package controller;

import model.Order;
import model.vo.OrderVO;
import service.CustomerService;
import service.OrderService;
import util.CounterDisplay;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * @author hiki on 2017-12-19
 */

@WebServlet("/orderList")
public class OrderList extends HttpServlet{

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        process(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        process(request, response);
    }


    // handle the session and cookie
    private void process(HttpServletRequest request, HttpServletResponse response) throws IOException {

        // find the cookie
        Cookie cookie = null;
        Cookie[] cookies = request.getCookies();
        if (null != cookies) {
            for (Cookie c : cookies) {
                if (c.getName().equals("login")) {
                    cookie = c;
                    break;
                }
            }
        }

        HttpSession session = request.getSession(false);

        // if session is not created
        if (null == session) {
            // if the user visit this page without logging in, redirect to login page
            String url = response.encodeRedirectURL(request.getContextPath() + "/login");
            response.sendRedirect(url);

        }
        else {
            // if the user does not log in
            if (null == session.getAttribute("login")) {
                // judge if the user attempt to log in
                String loginValue = request.getParameter("id");
                String passwordValue = request.getParameter("password");
                Cookie pwdCookie = new Cookie("password", passwordValue);
                // if so
                if (null != loginValue) {
                    // set the cookie if needed
                    if (null != cookie) {
                        if (!loginValue.equals(cookie.getValue())) {
                            cookie.setValue(loginValue);
                            response.addCookie(cookie);
                            response.addCookie(pwdCookie);
                        }
                    }
                    else {
                        cookie = new Cookie("login", loginValue);
                        cookie.setMaxAge(Integer.MAX_VALUE);
                        response.addCookie(cookie);
                        response.addCookie(pwdCookie);
                    }

                    // set attribute to request
                    request.setAttribute("id", loginValue);
                    request.setAttribute("password", passwordValue);

                    // display
                    display(request, response);

                }
                // if not
                else {
                    String url = response.encodeRedirectURL(request.getContextPath() + "/login");
                    response.sendRedirect(url);
                }

            }
            // if the user has logged in
            else {
                // add the session login value to request
                String id = request.getParameter("id");
                String password = request.getParameter("password");



                // set attribute to request
                request.setAttribute("id", id);
                request.setAttribute("password", password);

                display(request, response);
            }



        }


    }


    private void display(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // get the login info
        HttpSession session = request.getSession();

        boolean useSession = false;
        int customerId;
        String password;
        if (null != request.getAttribute("id")) {
            customerId = Integer.valueOf((String) request.getAttribute("id"));
            password = (String) request.getAttribute("password");
        }
        else {
            useSession = true;
            customerId = Integer.valueOf((String) session.getAttribute("login"));
            password = (String) session.getAttribute("password");
        }


        // login
        CustomerService cs = new CustomerService();
        if (!cs.login(customerId, password)) {
            if (!useSession) {
                session.removeAttribute("login");
                session.removeAttribute("password");
            }
            displayErrorPage(request, response);
        }
        else {
            if (!useSession) {
                // change session
                session.setAttribute("login", String.valueOf(customerId));
                session.setAttribute("password", password);
                session.removeAttribute("maxPageNum");
            }
            displayOrderList(request, response);
            displayPagingPage(request, response);
            CounterDisplay.displayUserCounter(request, response);
            displayLogoutPage(request, response);
        }

    }


    private void displayErrorPage(HttpServletRequest request, HttpServletResponse response) throws IOException {

        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html>\n" +
                "<html lang='en'>\n" +
                "<head>\n" +
                "    <meta charset='UTF-8'>\n" +
                "    <title>出错</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "<h1>用户名或密码错误！</h1>\n" +
                "<p>请仔细确认您的用户名及密码</p>" +
                "</body>\n" +
                "</html>");

    }

    private void displayOrderList(HttpServletRequest request, HttpServletResponse response) throws IOException {

        // get the login info
        int customerId = Integer.valueOf((String) request.getSession().getAttribute("login"));

        // get page
        int page = null != request.getParameter("page") ? Integer.valueOf(request.getParameter("page")) : 1;

        OrderService os = new OrderService();
        List<OrderVO> orders = os.findOrderVOByCustomerId(customerId, page);

        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html>\n" +
                "<html lang='en'>\n" +
                "<head>\n" +
                "    <meta charset='UTF-8'>\n" +
                "    <title>客户订单列表</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "<h1>客户订单列表</h1>\n");

        out.println("<table>");
        out.println("<tr><th>订单号</th><th>客户姓名</th><th>商品名称</th><th>数量</th><th>总价</th><th>创建时间</th></tr>");
        for (OrderVO order : orders) {
            out.println("<tr>");
            out.print("<td>" + order.getId() + "</td>");
            out.print("<td>" + order.getCustomerName() + "</td>");
            out.print("<td>" + order.getProductName() + "</td>");
            out.print("<td>"); out.print(order.getQuantity() > 0 ? order.getQuantity() : "<strong style='color:red'>缺货</strong>"); out.print("</td>");
            out.print("<td>" + order.getTotalPrice() + "</td>");
            out.print("<td>" + order.getCreatedAt().toString() + "</td>");
            out.println("</tr>");
        }
        out.println("</table>");
        out.println("</body>\n" +
                "</html>");

    }


    private void displayPagingPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // get max page num
        int maxPageNum;

        if (null != request.getSession().getAttribute("maxPageNum")) {
            maxPageNum = (Integer) request.getSession().getAttribute("maxPageNum");
        }
        else {
            OrderService os = new OrderService();
            int customerId = Integer.valueOf((String) request.getSession().getAttribute("login"));
            int count = os.findCount(customerId);
            maxPageNum = count / Order.PAGE_SIZE + (count%Order.PAGE_SIZE==0 ? 0 : 1);
            request.getSession().setAttribute("maxPageNum", maxPageNum);
        }


        // get page
        int page = null != request.getParameter("page") ? Integer.valueOf(request.getParameter("page")) : 1;

        // display paging
        PrintWriter out = response.getWriter();
        out.println("<table>");
        out.println("<tbody>\n<tr valign='top'>");
        // former page
        if (page != 1) {
            out.println("<td><a href='" + response.encodeURL(request.getContextPath()+"/orderList?page=" + (page-1)) + "'>上一页</a></td>");
        }
        // get the page num range
        int range = 7;
        int subRange = range / 2;
        int start = page - subRange;
        int end = page + subRange;

        if (range > maxPageNum) {
            start = 1;
            end = maxPageNum;
        }

        if (start < 1) {
            end += 1-start;
            start = 1;
        }

        if (end > maxPageNum) {
            start += maxPageNum-end;
            end = maxPageNum;
        }

        for (int i = start; i <= end; i++) {
            out.print("<td>");
            if (i != page) {
                out.print("<a href='" + response.encodeURL(request.getContextPath()+"/orderList?page=" + i) + "'>");
            }
            out.print(i);
            if (i != page) {
                out.print("</a>");
            }
            out.println("</td>");
        }

        // latter page
        if (maxPageNum > 0 && page != maxPageNum) {
            out.println("<td><a href='" + response.encodeURL(request.getContextPath()+"/orderList?page=" + (page+1)) + "'>下一页</a></td>");
        }

        out.println("</tr></tbody></table>");

    }

    private void displayLogoutPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        out.println("<form method='GET' action='" + response.encodeURL(request.getContextPath() + "/login") + "'>");
        out.println("<br/>");
        out.println("<input type='submit' name='logout' value='logout'>");
        out.println("</form>");
        out.println("</body></html>");
    }


}
