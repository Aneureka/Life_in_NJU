package test.cn.edu.nju.dao;

import cn.edu.nju.dao.OrderDao;
import cn.edu.nju.factory.DaoFactory;
import cn.edu.nju.model.Order;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * @author hiki on 2018-01-11
 */
public class OrderDaoTest {

    OrderDao tested;

    @Before
    public void setUp() throws Exception {
        tested = DaoFactory.getOrderDao();
    }

    @Test
    public void findAll() {
        assertNotNull(tested.findAll());
        System.out.println(tested.findAll());
    }

    @Test
    public void findByCustomerId() {
        assertNotNull(tested.findByCustomerId(4, 2));
    }

    @Test
    public void findCount() {
        System.out.println(tested.findCount(4));
    }

    @Test
    public void add() {
        Order order = new Order(4, 1111113, 222, 133.1);
        assertTrue(tested.add(order));
    }

    @Test
    public void addAll() {
    }

    @Test
    public void remove() {
    }
}