package cn.edu.nju.TrainingCollege.service;

import cn.edu.nju.TrainingCollege.common.exception.*;
import cn.edu.nju.TrainingCollege.domain.UserLoginForm;
import cn.edu.nju.TrainingCollege.domain.UserRegisterForm;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertNotNull;

/**
 * @author hiki on 2018-01-24
 */

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void addUser() throws EmailAlreadyExistsException {
        assertNotNull(userService.register(new UserRegisterForm("97257950@qq.com", "aneureka", "aneureka", "郭浩滨")));
    }

    @Test
    public void login() throws UserNotExistsException, UserNotActivatesException, WrongPasswordException, UserDisqualifiedException {
        assertNotNull(userService.login(new UserLoginForm("972579500@qq.com", "aneureka")));
    }

    @Test
    public void disqualificate() {
        userService.disqualify(1L);
    }
}