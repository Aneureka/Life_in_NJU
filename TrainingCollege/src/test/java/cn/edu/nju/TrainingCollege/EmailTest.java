package cn.edu.nju.TrainingCollege;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author hiki on 2018-01-24
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class EmailTest {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String username;

    @Test
    public void testSendSimple(){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(username);
        message.setTo("972579500@qq.com");
        message.setSubject("Spring Mail Test");
        message.setText("This is the simple content, thank you!");
        javaMailSender.send(message);
    }



}
