package cn.edu.nju.TrainingCollege.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author hiki on 2018-01-26
 */

@Entity
public class VerificationToken {

    private static final long EXPIRATION = 60 * 2;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * this token is used for account verifying
     * 1. generate the VerificationToken for the User and persist it
     * 2. send out the email message for account confirmation – which includes a confirmation link with the VerificationToken’s value
     * use [Spring Event] to create the token and send the email
     */
    @Column(nullable = false)
    private String token;
    /**
     * the time that the link becomes invalid
     */
    @Column(nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime expiryTime;
    /**
     * this is the user the token binds with
     */
    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    public VerificationToken() {
    }

    public VerificationToken(String token, User user) {
        this.token = token;
        this.user = user;
        this.expiryTime = calculateExpiryTime();
    }

    private LocalDateTime calculateExpiryTime() {
        return LocalDateTime.now().plusMinutes(EXPIRATION);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getExpiryTime() {
        return expiryTime;
    }

    public void setExpiryTime(LocalDateTime expiryTime) {
        this.expiryTime = expiryTime;
    }
}
