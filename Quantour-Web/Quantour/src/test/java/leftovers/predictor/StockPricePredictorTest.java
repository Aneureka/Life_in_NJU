package leftovers.predictor;

import leftovers.service.stockpricepredictimpl.StockPricePredictor;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by Hiki on 2017/6/4.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class StockPricePredictorTest {

    @Autowired
    StockPricePredictor tested;

    @Before
    public void setUp() throws Exception {
//        tested = new StockPricePredictor("000001.SZ");
    }

    @Test
    public void predictNextPrice() throws Exception {
        tested.initialize("000001.SZ");
        System.out.println(tested.predictNextPrice());
    }

    @Test
    public void predictNextMPrice() throws Exception {
        tested.initialize("000001.SZ");
        List<Double> prices = tested.predictNextMPrice(3);
        for (Double each : prices) {
            System.out.print(each + " ");
        }
    }

}