package cn.edu.nju.TrainingCollege.entity;

import cn.edu.nju.TrainingCollege.common.constant.InstituteApplicationStatus;
import cn.edu.nju.TrainingCollege.domain.InstituteApplicationForm;
import cn.edu.nju.TrainingCollege.domain.InstituteProfileModifyForm;
import cn.edu.nju.TrainingCollege.domain.InstituteRegisterForm;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.Email;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author hiki on 2018-01-29
 */

@Entity
public class InstituteApplicationLog {

    /**
     * application's id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * institute's id
     */
    private Long instituteId;
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
     * password used for login
     */
    @Column(nullable = false)
    private String password;
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
     * application created time
     */
    @Column(nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    /**
     * the status of the application
     */
    @Column(nullable = false)
    private InstituteApplicationStatus status;
    /**
     * modifying advice when disapproving the application
     */
    private String advice;
    /**
     * processing time
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime processedAt;

    public InstituteApplicationLog() {
    }

    private InstituteApplicationLog(String name, String description, String email, String password, String address, String teacherInfo) {
        this.name = name;
        this.description = description;
        this.email = email;
        this.password = password;
        this.address = address;
        this.teacherInfo = teacherInfo;
        this.createdAt = LocalDateTime.now();
        this.status = InstituteApplicationStatus.UNPROCESSED;
        this.advice = "";
        this.processedAt = null;
    }

    private InstituteApplicationLog(Long instituteId, String name, String description, String email, String password, String address, String teacherInfo, LocalDateTime createdAt) {
        this.instituteId = instituteId;
        this.name = name;
        this.description = description;
        this.email = email;
        this.password = password;
        this.address = address;
        this.teacherInfo = teacherInfo;
        this.createdAt = createdAt;
        this.status = InstituteApplicationStatus.UNPROCESSED;
        this.advice = "";
        this.processedAt = LocalDateTime.now();
    }

    public InstituteApplicationLog(Long id, Long instituteId, String name, String description, String email, String password, String address, String teacherInfo, LocalDateTime createdAt, InstituteApplicationStatus status, String advice, LocalDateTime processedAt) {
        this.id = id;
        this.instituteId = instituteId;
        this.name = name;
        this.description = description;
        this.email = email;
        this.password = password;
        this.address = address;
        this.teacherInfo = teacherInfo;
        this.createdAt = createdAt;
        this.status = status;
        this.advice = advice;
        this.processedAt = processedAt;
    }

    public static InstituteApplicationLog fromInstituteRegistrationForm(InstituteRegisterForm form) {
        InstituteApplicationLog log = new InstituteApplicationLog(
                form.getName(),
                form.getDescription(),
                form.getEmail(),
                form.getPassword(),
                form.getAddress(),
                form.getTeacherInfo());
        if (form.getInstituteId() != null) {
            log.setInstituteId(form.getInstituteId());
        }
        return log;
    }

    public static InstituteApplicationLog fromInstituteAndModifyForm(Institute institute, InstituteProfileModifyForm form) {
        return new InstituteApplicationLog(
                institute.getId(),
                form.getName(),
                form.getDescription(),
                institute.getEmail(),
                institute.getPassword(),
                form.getAddress(),
                form.getTeacherInfo(),
                LocalDateTime.now()
        );
    }

    public static InstituteApplicationLog fromInstituteApplicationForm(InstituteApplicationForm form) {

        InstituteApplicationLog log = new InstituteApplicationLog(
                form.getId(),
                form.getInstituteId(),
                form.getName(),
                form.getDescription(),
                form.getEmail(),
                form.getPassword(),
                form.getAddress(),
                form.getTeacherInfo(),
                LocalDateTime.parse(form.getCreatedAt(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                form.isApproved() ? InstituteApplicationStatus.APPROVED : InstituteApplicationStatus.DISAPPROVED,
                form.getAdvice(),
                LocalDateTime.now()
        );
        return log;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getInstituteId() {
        return instituteId;
    }

    public void setInstituteId(Long instituteId) {
        this.instituteId = instituteId;
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

    public String getTeacherInfo() {
        return teacherInfo;
    }

    public void setTeacherInfo(String teacherInfo) {
        this.teacherInfo = teacherInfo;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public InstituteApplicationStatus getStatus() {
        return status;
    }

    public void setStatus(InstituteApplicationStatus status) {
        this.status = status;
    }

    public String getAdvice() {
        return advice;
    }

    public void setAdvice(String advice) {
        this.advice = advice;
    }

    public LocalDateTime getProcessedAt() {
        return processedAt;
    }

    public void setProcessedAt(LocalDateTime processedAt) {
        this.processedAt = processedAt;
    }
}
