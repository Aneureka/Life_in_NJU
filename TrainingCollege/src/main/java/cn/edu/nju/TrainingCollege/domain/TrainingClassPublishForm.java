package cn.edu.nju.TrainingCollege.domain;

/**
 * @author hiki on 2018-04-03
 */

public class TrainingClassPublishForm {

    private String teacher;

    private Integer capacity;

    public TrainingClassPublishForm() {
    }

    public TrainingClassPublishForm(String teacher, Integer capacity) {
        this.teacher = teacher;
        this.capacity = capacity;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }
}
