package cn.edu.nju.TrainingCollege.entity;

import cn.edu.nju.TrainingCollege.common.constant.UserStatus;
import cn.edu.nju.TrainingCollege.domain.UserRegisterForm;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author hiki on 2018-01-24
 */

@Entity
public class User {

    /**
     * user's unique id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * user's email, used for verifying and logging in
     */
    @Column(nullable = false)
    private String email;
    /**
     * user's password
     */
    @Column(nullable = false)
    private String password;
    /**
     * user's name
     */
    @Column(nullable = false)
    private String name;
    /**
     * creating time
     */
    @Column(nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    /**
     * balance in the member card
     */
    private Double balance;
    /**
     * member level
     */
    @Column(nullable = false)
    private Integer rate;
    /**
     * credit, used for exchanging balance in the member card
     */
    @Column(nullable = false)
    private Integer credit;
    /**
     * whether the account is enabled
     * may be activated through email, or disabled by administrator
     */
    @Column(nullable = false)
    private UserStatus status;
    /**
     * training orders
     */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TrainingOrder> orders;

    public User() {
    }

    public User(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.createdAt = LocalDateTime.now();
        this.balance = 0.0;
        this.rate = 0;
        this.credit = 0;
        this.status = UserStatus.INACTIVATED;
        this.createdAt = LocalDateTime.now();
        this.orders = new ArrayList<>();
    }

    public static User fromUserRegisterForm(UserRegisterForm form) {
        return new User(form.getEmail(), form.getPassword(), form.getName());

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public Integer getCredit() {
        return credit;
    }

    public void setCredit(Integer credit) {
        this.credit = credit;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public List<TrainingOrder> getOrders() {
        return orders;
    }

    public void setOrders(List<TrainingOrder> orders) {
        this.orders = orders;
    }
}
