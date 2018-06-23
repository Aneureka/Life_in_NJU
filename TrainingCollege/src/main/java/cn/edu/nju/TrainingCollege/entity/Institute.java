package cn.edu.nju.TrainingCollege.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.Email;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author hiki on 2018-01-28
 */

@Entity
public class Institute {
    /**
     * institute's unique id, used for login
     */
    @Id
    @TableGenerator(name = "id_gen", initialValue = 1000000, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "id_gen")
    @Column(length = 7)
    private Long id;
    /**
     * password used for login
     */
    @Column(nullable = false)
    private String password;
    /**
     * institute's name
     */
    @Column(nullable = false)
    private String name;
    /**
     * institute's description, including lesson provided, price, etc
     */
    @Column(columnDefinition = "longtext")
    private String description;
    /**
     * email for contact, and send back the verifying result
     */
    @Email
    @Column(nullable = false)
    private String email;
    /**
     * institute's address
     */
    @Column(nullable = false)
    private String address;
    /**
     * teacher information
     */
    private String teacherInfo;
    /**
     * institute's creating time
     */
    @Column(nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    /**
     * institute's profit
     */
    private Double profit;
    /**
     * training plan in the future
     */
    @OneToMany(mappedBy = "institute", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<TrainingPlan> trainingPlans;

    public Institute() {
    }

    public Institute(String password, String name, String description, String email, String address, LocalDateTime createdAt, Double profit, String teacherInfo, List<TrainingPlan> trainingPlans) {
        this.password = password;
        this.name = name;
        this.description = description;
        this.email = email;
        this.address = address;
        this.createdAt = createdAt;
        this.profit = profit;
        this.teacherInfo = teacherInfo;
        this.trainingPlans = trainingPlans;
    }

    public Institute(String password, String name, String description, String email, String address, String teacherInfo) {
        this.password = password;
        this.name = name;
        this.description = description;
        this.email = email;
        this.address = address;
        this.createdAt = LocalDateTime.now();
        this.profit = 0.0;
        this.teacherInfo = teacherInfo;
        this.trainingPlans = new ArrayList<>();
    }

    public Institute(String password, String name, String description, String email, String address, String teacherInfo, LocalDateTime createdAt) {
        this.password = password;
        this.name = name;
        this.description = description;
        this.email = email;
        this.address = address;
        this.teacherInfo = teacherInfo;
        this.createdAt = createdAt;
        this.profit = 0.0;
        this.teacherInfo = teacherInfo;
        this.trainingPlans = new ArrayList<>();
    }

    public static Institute fromInstituteApplicationLog(InstituteApplicationLog log) {
        return new Institute(
                log.getPassword(),
                log.getName(),
                log.getDescription(),
                log.getEmail(),
                log.getAddress(),
                log.getTeacherInfo(),
                log.getCreatedAt()
        );
    }

    public Institute updateFromInstituteApplicationLog(InstituteApplicationLog log) {
        if (!name.equals(log.getName()))
            name = log.getName();
        if (!description.equals(log.getDescription()))
            description = log.getDescription();
        if (!email.equals(log.getEmail()))
            email = log.getEmail();
        if (!address.equals(log.getAddress()))
            address = log.getAddress();
        if (!teacherInfo.equals(log.getTeacherInfo()))
            teacherInfo = log.getTeacherInfo();
        return this;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Double getProfit() {
        return profit;
    }

    public void setProfit(Double profit) {
        this.profit = profit;
    }

    public String getTeacherInfo() {
        return teacherInfo;
    }

    public void setTeacherInfo(String teacherInfo) {
        this.teacherInfo = teacherInfo;
    }

    public List<TrainingPlan> getTrainingPlans() {
        return trainingPlans;
    }

    public void setTrainingPlans(List<TrainingPlan> trainingPlans) {
        this.trainingPlans = trainingPlans;
    }
}
