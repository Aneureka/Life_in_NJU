package repository;

import model.Product;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author hiki on 2017-12-19
 */
public class ProductRepositoryTest {

    ProductRepository tested;

    @Before
    public void setUp() throws Exception {
        tested = new ProductRepository();
    }

    @Test
    public void findAll() {
        System.out.println(tested.findAll());
    }

    @Test
    public void findByProductId() {
        System.out.println(tested.findByProductId(11));
    }

    @Test
    public void add() {
        Product product = new Product("电动牙刷", 250.0, 2);
        assertEquals(true, tested.add(product));
    }

    @Test
    public void addAll() {
        for (int i = 0; i < 900; i++) {
            String name = RandomStringUtils.random(10, true, false);
            double price = RandomUtils.nextDouble();
            int quantity = RandomUtils.nextInt(100);
            Product product = new Product(name, price, quantity);
            assertEquals(true, tested.add(product));
        }
    }

    @Test
    public void remove() {
        assertEquals(true, tested.remove(1));
    }
}