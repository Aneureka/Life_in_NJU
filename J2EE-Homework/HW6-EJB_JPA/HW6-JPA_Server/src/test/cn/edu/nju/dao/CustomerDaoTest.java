package test.cn.edu.nju.dao;

import cn.edu.nju.dao.CustomerDao;
import cn.edu.nju.factory.DaoFactory;
import cn.edu.nju.model.Customer;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * @author hiki on 2018-01-11
 */
public class CustomerDaoTest {

    private CustomerDao tested;

    @Before
    public void setUp() throws Exception {
        tested = DaoFactory.getCustomerDao();
    }

    @Test
    public void findAll() {
        assertNotNull(tested.findAll());
        System.out.println(tested.findAll());
    }

    @Test
    public void findByCustomerId() {
        assertNotNull(tested.findByCustomerId(4));
    }

    @Test
    public void login() {
        assertTrue(tested.login(4, "1"));
    }

    @Test
    public void add() {
        Customer c = new Customer(15125001, "123", "Hiki");
        System.out.println(tested.add(c));
    }

    @Test
    public void isExisted() {
        assertTrue(tested.isExisted(4));
    }
}