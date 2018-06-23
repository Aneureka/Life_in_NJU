package cn.edu.nju.TrainingCollege.domain;

import cn.edu.nju.TrainingCollege.entity.InstituteApplicationLog;

import java.time.format.DateTimeFormatter;

/**
 * @author hiki on 2018-03-13
 */

public class InstituteApplicationForm {

    private Long id;

    private Long instituteId;

    private String name;

    private String description;

    private String email;

    private String password;

    private String address;

    private String teacherInfo;

    private String createdAt;

    private boolean approved;

    private String advice;

    public InstituteApplicationForm() {
    }

    public InstituteApplicationForm(Long id, Long instituteId, String name, String description, String email, String password, String address, String teacherInfo, String createdAt) {
        this.id = id;
        this.instituteId = instituteId;
        this.name = name;
        this.description = description;
        this.email = email;
        this.password = password;
        this.address = address;
        this.teacherInfo = teacherInfo;
        this.createdAt = createdAt;
        this.approved = false;
        this.advice = "";
    }

    public static InstituteApplicationForm fromInstituteApplicationLog(InstituteApplicationLog log) {
        return new InstituteApplicationForm(
                log.getId(),
                log.getInstituteId(),
                log.getName(),
                log.getDescription(),
                log.getEmail(),
                log.getPassword(),
                log.getAddress(),
                log.getTeacherInfo(),
                log.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        );
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public String getAdvice() {
        return advice;
    }

    public void setAdvice(String advice) {
        this.advice = advice;
    }
}
