package service.impl;

import dao.OrderDao;
import factory.DaoFactory;
import model.Order;
import model.vo.OrderVO;
import service.OrderService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author hiki on 2017-12-20
 */

public class OrderServiceImpl implements OrderService {

    private OrderDao orderDao;

    private static class holder {
        private static final OrderServiceImpl INSTANCE = new OrderServiceImpl();
    }

    private OrderServiceImpl() {
        orderDao = DaoFactory.getOrderDao();
    }

    public static final OrderServiceImpl getInstance() {
        return OrderServiceImpl.holder.INSTANCE;
    }


    @Override
    public List<OrderVO> findOrderVOByCustomerId(int customerId, int page) {
        List<Order> orders = orderDao.findByCustomerId(customerId, page);
        return orders.stream().map(OrderVO::fromEntity).collect(Collectors.toList());
    }

    @Override
    public int findCount(int customerId) {
        return orderDao.findCount(customerId);
    }
}
