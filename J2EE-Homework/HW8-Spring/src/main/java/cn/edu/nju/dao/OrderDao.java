package cn.edu.nju.dao;


import cn.edu.nju.model.Order;
import java.util.List;

/**
 * @author hiki on 2017-12-30
 */

public interface OrderDao {

    List<Order> findAll();

    List<Order> findByCustomerId(int id, int page);

    Order findByOrderId(int id);

    int findCount(int id);

    boolean add(Order order);

    boolean addAll(List<Order> orders);

    boolean remove(int id);


}
