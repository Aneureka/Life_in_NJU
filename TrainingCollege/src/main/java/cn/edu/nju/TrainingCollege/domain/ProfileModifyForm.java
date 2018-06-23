package cn.edu.nju.TrainingCollege.domain;

/**
 * @author hiki on 2018-03-12
 */

public class ProfileModifyForm {

    private String name;

    public ProfileModifyForm() {
    }

    public ProfileModifyForm(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
