package cn.edu.nju.TrainingCollege.service;

import cn.edu.nju.TrainingCollege.common.exception.BalanceInsufficientException;
import cn.edu.nju.TrainingCollege.common.exception.ClassAssignFailedException;
import cn.edu.nju.TrainingCollege.common.exception.WrongPasswordException;
import cn.edu.nju.TrainingCollege.domain.Lesson;
import cn.edu.nju.TrainingCollege.domain.TrainingOrderForm;
import cn.edu.nju.TrainingCollege.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hiki on 2018-04-06
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class TrainingOrderServiceTest {


    @Autowired
    private TrainingOrderService service;

    @Autowired
    private UserService userService;

    @Autowired
    private TrainingPlanService trainingPlanService;

    @Test
    public void create() throws BalanceInsufficientException, WrongPasswordException, ClassAssignFailedException {
        User user = userService.findByEmail("972579500@qq.com");
        Lesson lesson = trainingPlanService.getLessons().get(0);
        List<String> studentNames = new ArrayList<>();
        studentNames.add("第四个");
        studentNames.add("第五个");
        studentNames.add("第六个");
        studentNames.add("第七个");
        studentNames.add("第八个");
        TrainingOrderForm form = new TrainingOrderForm(lesson.getId(), "none", studentNames);
        service.create(user, form);
    }
}