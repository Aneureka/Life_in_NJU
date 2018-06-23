package cn.edu.nju.dao;

import cn.edu.nju.factory.DaoFactory;
import org.junit.Before;
import org.junit.Test;

/**
 * @author hiki on 2018-01-12
 */
public class CustomerDaoTest {

    CustomerDao tested;

    @Before
    public void setUp() throws Exception {
        tested = DaoFactory.getCustomerDao();
    }

    @Test
    public void findAll() {
        System.out.println(tested.findAll());
    }

    @Test
    public void findByCustomerId() {
        System.out.println(tested.findByCustomerId(4));
    }

    @Test
    public void login() {
        System.out.println(tested.login(4, "1"));
    }

    @Test
    public void add() {
    }

    @Test
    public void isExisted() {
    }
}