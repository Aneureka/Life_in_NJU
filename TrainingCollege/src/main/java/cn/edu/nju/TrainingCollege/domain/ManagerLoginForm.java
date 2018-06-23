package cn.edu.nju.TrainingCollege.domain;

/**
 * @author hiki on 2018-03-12
 */

public class ManagerLoginForm {

    private Long id;

    private String password;

    public ManagerLoginForm() {
    }

    public ManagerLoginForm(Long id, String password) {
        this.id = id;
        this.password = password;
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
}
