package cn.edu.nju.TrainingCollege.service;

import cn.edu.nju.TrainingCollege.common.exception.EmailAlreadyExistsException;
import cn.edu.nju.TrainingCollege.domain.InstituteRegisterForm;
import cn.edu.nju.TrainingCollege.entity.InstituteApplicationLog;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @author hiki on 2018-01-30
 */

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class InstituteServiceTest {

    @Autowired
    private InstituteService instituteService;

    @Test
    public void register() throws EmailAlreadyExistsException {

        InstituteRegisterForm form = new InstituteRegisterForm(
                "972579500@qq.com",
                "password",
                "password",
                "广通驾校",
                "默认描述",
                "南京市",
                ""
        );

        instituteService.register(form);

    }

    @Test
    public void showAllLogs() {
        List<InstituteApplicationLog> logs = instituteService.getApplicationLogs();
        System.out.println(logs);
    }
}