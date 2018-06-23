package cn.edu.nju.TrainingCollege.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author hiki on 2018-04-06
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class TrainingOrderManagerTest {

    @Autowired
    private TrainingOrderManager manager;

    @Test
    public void autoCancelUnpaidOrders() {
        manager.autoCancelUnpaidOrders();
    }
}