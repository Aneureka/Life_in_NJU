package cn.edu.nju.dao;

import cn.edu.nju.factory.DaoFactory;
import cn.edu.nju.model.Order;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertNotNull;

/**
 * @author hiki on 2018-01-12
 */
public class OrderDaoTest {

    @Autowired
    OrderDao tested;

    @Before
    public void setUp() throws Exception {
//        tested = DaoFactory.getOrderDao();
    }

    @Test
    public void findAll() {
        assertNotNull(tested.findAll());
    }

    @Test
    public void findByCustomerId() {
        assertNotNull(tested.findByCustomerId(4, 2));
    }

    @Test
    public void findByOrderId() {
        assertNotNull(tested.findByOrderId(2));
    }

    @Test
    public void findCount() {
        assertNotNull(tested.findCount(4));
    }

    @Test
    public void add() {
        Order order = new Order(1, 222, 1, 2.23);
        tested.add(order);
    }

    @Test
    public void addAll() {
    }

    @Test
    public void remove() {
    }
}