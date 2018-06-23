package cn.edu.nju.TrainingCollege.configuration.listener;

import org.springframework.context.ApplicationEvent;

/**
 * @author hiki on 2018-01-26
 */

public class EmailActivationEvent extends ApplicationEvent {
    /**
     * app url used for producing validation link
     */
    private String appUrl;
    /**
     * email of the user to validate
     */
    private String email;

    public EmailActivationEvent(String appUrl, String email) {
        super(email);
        this.appUrl = appUrl;
        this.email = email;
    }

    public String getAppUrl() {
        return appUrl;
    }

    public void setAppUrl(String appUrl) {
        this.appUrl = appUrl;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
