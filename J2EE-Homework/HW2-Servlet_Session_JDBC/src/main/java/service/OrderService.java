package service;

import model.Order;
import model.vo.OrderVO;
import repository.OrderRepository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author hiki on 2017-12-20
 */

public class OrderService {

    private OrderRepository or;

    public OrderService() {
        or = new OrderRepository();
    }

    public List<OrderVO> findOrderVOByCustomerId(int customerId, int page) {
        List<Order> orders = or.findByCustomerId(customerId, page);
        return orders.stream().map(OrderVO::fromEntity).collect(Collectors.toList());
    }

    public int findCount(int customerId) {
        return or.findCount(customerId);
    }
}
