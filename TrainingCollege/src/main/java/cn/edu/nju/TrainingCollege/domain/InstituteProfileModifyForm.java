package cn.edu.nju.TrainingCollege.domain;

/**
 * @author hiki on 2018-04-03
 */

public class InstituteProfileModifyForm {

    private String name;

    private String description;

    private String address;

    private String teacherInfo;

    public InstituteProfileModifyForm() {
    }

    public InstituteProfileModifyForm(String name, String description, String address, String teacherInfo) {
        this.name = name;
        this.description = description;
        this.address = address;
        this.teacherInfo = teacherInfo;
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
