package cn.edu.nju.TrainingCollege.repository;

import cn.edu.nju.TrainingCollege.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author hiki on 2018-01-24
 */

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class UserRepositoryTest {

    @Autowired
    private UserRepository tested;

    @Test
    public void testSave() {
        tested.save(new User("972579500@qq.com", "aneureka", "郭浩滨"));
    }

}