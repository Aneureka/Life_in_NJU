package leftovers.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * Created by Hiki on 2017/6/6.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class StockServiceTest {

    @Autowired
    StockService tested;
    String code = "000001.SZ";

//    @Test
//    public void predictNextPrice() throws Exception {
//        String json = tested.predictNextPrice(code);
//        System.out.println(json);
//    }
//
//    @Test
//    public void predictNextMPrice() throws Exception {
//        String json = tested.predictNextMPrice(code, 5);
//        System.out.println(json);
//    }

}