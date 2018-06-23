package cn.edu.nju.controller;

import cn.edu.nju.factory.ServiceFactory;
import cn.edu.nju.model.vo.OrderVO;
import cn.edu.nju.service.OrderService;
import cn.edu.nju.util.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * @author hiki on 2017-12-19
 */

@WebServlet("/orderList")
@Component
public class OrderListServlet extends HttpServlet {

    @Autowired
    private OrderService os;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        process(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        process(request, response);
    }

    private void process(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        // check if logs in
        if (request.getSession().getAttribute("login") != null) {
            processOrderList(request);
            processPaging(request);
        }
        request.getRequestDispatcher("/jsp/orderListPage.jsp").forward(request, response);
    }

    private void processOrderList(HttpServletRequest request) {
        // get the login info
        int customerId = (int) request.getSession().getAttribute("login");

        // get pageNum
        int pageNum = null != request.getParameter("pageNum") ? Integer.valueOf(request.getParameter("pageNum")) : 1;

        List<OrderVO> orders = os.findOrderVOByCustomerId(customerId, pageNum);

        request.setAttribute("orders", orders);
    }

    private void processPaging(HttpServletRequest request) {
        // set maxPageNum in session if not exists
        HttpSession session = request.getSession();
        if (session.getAttribute("maxPageNum") == null) {
            int customerId = (int) session.getAttribute("login");
            int count = os.findCount(customerId);
            session.setAttribute("maxPageNum", count / PageHelper.PAGE_SIZE + (count% PageHelper.PAGE_SIZE==0 ? 0 : 1));
        }

        // set page to default [1] in request if not exists
        if (request.getParameter("pageNum") != null) {
            request.setAttribute("pageNum", Integer.valueOf((String)request.getParameter("pageNum")));
        } else {
            request.setAttribute("pageNum", 1);
        }

        int maxPageNum = (int) session.getAttribute("maxPageNum");
        int pageNum = (int) request.getAttribute("pageNum");

        // get the page num range
        int range = 7;
        int subRange = range / 2;
        int start = pageNum - subRange;
        int end = pageNum + subRange;
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
        // set the start and end
        request.setAttribute("start", start);
        request.setAttribute("end", end);
    }

}
