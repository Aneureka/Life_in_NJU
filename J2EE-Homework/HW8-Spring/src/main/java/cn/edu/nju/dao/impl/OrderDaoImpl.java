package cn.edu.nju.dao.impl;

import cn.edu.nju.dao.OrderDao;
import cn.edu.nju.model.Order;
import cn.edu.nju.util.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;


/**
 * @author hiki on 2017-12-19
 */

@Repository
@Transactional
public class OrderDaoImpl implements OrderDao {

    @Autowired
    private AbstractDao<Order> dao;


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
