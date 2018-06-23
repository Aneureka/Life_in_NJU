package cn.edu.nju.dao;

import cn.edu.nju.factory.DaoFactory;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

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
    }

    @Test
    public void login() {
    }

    @Test
    public void add() {
    }

    @Test
    public void isExisted() {
    }
}