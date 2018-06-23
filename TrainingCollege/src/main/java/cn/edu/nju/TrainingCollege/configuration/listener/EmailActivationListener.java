package cn.edu.nju.TrainingCollege.configuration.listener;

import cn.edu.nju.TrainingCollege.common.EmailHandler;
import cn.edu.nju.TrainingCollege.entity.User;
import cn.edu.nju.TrainingCollege.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * @author hiki on 2018-01-26
 */

@Component
public class EmailActivationListener implements ApplicationListener<EmailActivationEvent> {

    @Autowired
    private UserService service;

    @Autowired
    private EmailHandler emailHandler;

    @Value("${trainingCollege.host}")
    private String host;

    @Override
    public void onApplicationEvent(EmailActivationEvent emailActivationEvent) {
        activateEmail(emailActivationEvent);
    }

    private void activateEmail(EmailActivationEvent event) {
        // create a token
        String token = UUID.randomUUID().toString();
        String email = event.getEmail();
        User user = service.findByEmail(email);
        service.createVerificationToken(user, token);

        // send the email to activate account
        String subject = "账号激活";
        String confirmationUrl = host + event.getAppUrl() + "/api/users/registrationConfirm?token=" + token;
        String text = "点击链接激活：" + confirmationUrl;
        emailHandler.sendSimpleEmail(email, subject, text);
    }

}
