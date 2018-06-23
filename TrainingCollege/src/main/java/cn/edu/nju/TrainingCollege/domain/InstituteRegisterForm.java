package cn.edu.nju.TrainingCollege.domain;

/**
 * @author hiki on 2018-01-30
 */

public class InstituteRegisterForm {

    private Long instituteId;

    private String email;

    private String password;

    private String confirmPassword;

    private String name;

    private String description;

    private String address;

    private String teacherInfo;

    public InstituteRegisterForm() {
    }

    public InstituteRegisterForm(String email, String password, String confirmPassword, String name, String description, String address, String teacherInfo) {
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.name = name;
        this.description = description;
        this.address = address;
        this.teacherInfo = teacherInfo;
    }

    public Long getInstituteId() {
        return instituteId;
    }

    public void setInstituteId(Long instituteId) {
        this.instituteId = instituteId;
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

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
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
}
