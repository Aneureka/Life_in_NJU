package cn.edu.nju.TrainingCollege.domain;

import cn.edu.nju.TrainingCollege.entity.Student;

import java.time.format.DateTimeFormatter;

/**
 * @author hiki on 2018-04-06
 */

public class StudentForLesson {

    private Long id;

    private String name;

    private String teacher;

    private Long orderId;

    private String beganAt;

    private Double score;

    private String quit;

    public StudentForLesson() {
    }

    public StudentForLesson(Long id, String name, String teacher, Long orderId, String beganAt, Double score, String quit) {
        this.id = id;
        this.name = name;
        this.teacher = teacher;
        this.orderId = orderId;
        this.beganAt = beganAt;
        this.score = score;
        this.quit = quit;
    }

    public static StudentForLesson fromStudent(Student student) {
        String beganAt = student.getBeganAt() == null ? "未开始课程" : student.getBeganAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        return new StudentForLesson(
                student.getId(),
                student.getName(),
                student.getTrainingClass().getTeacher(),
                student.getTrainingOrder().getId(),
                beganAt,
                student.getScore(),
                student.getQuit() ? "是" : "否"
        );
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getBeganAt() {
        return beganAt;
    }

    public void setBeganAt(String beganAt) {
        this.beganAt = beganAt;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public String getQuit() {
        return quit;
    }

    public void setQuit(String quit) {
        this.quit = quit;
    }
}
