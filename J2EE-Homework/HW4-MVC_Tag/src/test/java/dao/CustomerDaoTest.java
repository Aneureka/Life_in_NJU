package dao;

import factory.DaoFactory;
import model.Customer;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author hiki on 2017-12-19
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
        System.out.println(tested.findByCustomerId(151250048));
    }

    @Test
    public void login() {
        assertEquals(true, tested.login(151250048, "1"));
    }

    @Test
    public void add() {

        Customer c = new Customer(141250043, "1", "啊哈");
        assertEquals(true, tested.add(c));
    }
}