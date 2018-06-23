package cn.edu.nju.dao.impl;

import cn.edu.nju.dao.OrderDao;
import cn.edu.nju.model.Order;
import cn.edu.nju.util.PageHelper;

import java.util.List;


/**
 * @author hiki on 2017-12-19
 */

public class OrderDaoImpl implements OrderDao {

    private AbstractDao<Order> dao;

    private static class holder {
        private static final OrderDaoImpl INSTANCE = new OrderDaoImpl();
    }

    private OrderDaoImpl() {
        dao = new AbstractDao<>(Order.class);
    }

    public static OrderDaoImpl getInstance() {
        return holder.INSTANCE;
    }


    /*========================================== method ===============================================*/

    @Override
    public List<Order> findAll() {
        return dao.findAll();
    }

    @Override
    public List<Order> findByCustomerId(int id, int page) {
        String query = "SELECT * FROM orders o WHERE o.cid=" + id +
                " LIMIT " + (page-1)* PageHelper.PAGE_SIZE + "," + PageHelper.PAGE_SIZE;
        return dao.findByNativeQuery(query);
    }

    @Override
    public Order findByOrderId(int id) {
        return dao.findOne(id);
    }

    @Override
    public int findCount(int id) {
        String query = "SELECT COUNT(o) FROM " + Order.class.getName() + " o WHERE o.customerId=" + id;
        return dao.findCountByQuery(query);
    }

    @Override
    public boolean add(Order order) {
        return dao.create(order);
    }

    @Override
    public boolean addAll(List<Order> orders) {
        return dao.createBatch(orders);
    }

    @Override
    public boolean remove(int id) {
        return dao.removeById(id);
    }
}
