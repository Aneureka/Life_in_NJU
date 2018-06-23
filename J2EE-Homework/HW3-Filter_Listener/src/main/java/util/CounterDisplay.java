package util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author hiki on 2017-12-22
 */

public class CounterDisplay {

    public static void displayUserCounter(HttpServletRequest request, HttpServletResponse response) throws IOException {

        int userCount = (int) request.getServletContext().getAttribute("userCount");
        int loginUserCount = (int) request.getServletContext().getAttribute("loginUserCount");

        PrintWriter writer = response.getWriter();
        writer.println("<hr/><p>");
        writer.println("在线总人数: " + userCount + " ");
        writer.println("已登录人数: " + loginUserCount + " ");
        writer.println("游客人数: " + (userCount-loginUserCount) + "</p>");
    }
}
