package cn.edu.nju.TrainingCollege.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

/**
 * @author hiki on 2018-01-27
 */

@Component
public class EmailHandler {

    @Value("${spring.mail.username}")
    private String from;

    @Autowired
    private JavaMailSender sender;

    public void sendSimpleEmail(String to, String subject, String text) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setFrom(from);
        email.setTo(to);
        email.setSubject(subject);
        email.setText(text);
        sender.send(email);
    }

}
