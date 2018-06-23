package repository;

import model.Order;
import org.apache.commons.lang.math.RandomUtils;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author hiki on 2017-12-19
 */
public class OrderRepositoryTest {

    OrderRepository tested;

    @Before
    public void setUp() throws Exception {
        tested = new OrderRepository();
    }

    @Test
    public void findAll() {
        System.out.println(tested.findAll());

    }

    @Test
    public void findByCustomerId() {
        System.out.println(tested.findByCustomerId(1, 1));
    }

    @Test
    public void findByOrderId() {
        System.out.println(tested.findByOrderId(1));
    }

    @Test
    public void findCount() {
        System.out.println(tested.findCount(4));
    }

    @Test
    public void add() {
        Order order = new Order(1, 2, 4, 20.0);
        assertEquals(true, tested.add(order));
    }

    @Test
    public void addAll() {
        for (int i = 0; i < 1000; i++) {
            int customerId = RandomUtils.nextInt(5) + 1;
            int productId = RandomUtils.nextInt(5) + 1;
            int quantity = RandomUtils.nextInt(10) + 1;
            double totalPrice = RandomUtils.nextDouble();
            Order order = new Order(customerId, productId, quantity, totalPrice);
            assertEquals(true, tested.add(order));
        }
    }

    @Test
    public void remove() {
        assertEquals(true, tested.remove(1));
    }
}