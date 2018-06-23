package cn.edu.nju.TrainingCollege.repository;

import cn.edu.nju.TrainingCollege.entity.Institute;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author hiki on 2018-01-28
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class InstituteRepositoryTest {

    @Autowired
    private InstituteRepository instituteRepository;


    @Test
    public void test() {
        Institute institute = instituteRepository.save(new Institute("111222", "广通驾校", "垃圾的一批", "972579500@qq.com", "江苏省南京市", ""));
    }
}