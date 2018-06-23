package cn.edu.nju.TrainingCollege.domain;

import cn.edu.nju.TrainingCollege.entity.Student;

/**
 * @author hiki on 2018-04-07
 */

public class StudentDetailInfo {

    private String name;

    private String teacher;

    private String beganAt;

    private Double score;

    public StudentDetailInfo() {
    }

    public StudentDetailInfo(String name, String teacher, String beganAt, Double score) {
        this.name = name;
        this.teacher = teacher;
        this.beganAt = beganAt;
        this.score = score;
    }

    public static StudentDetailInfo fromStudent(Student student) {
        String name = student.getName();
        String teacher = student.getTrainingClass() != null ? student.getTrainingClass().getTeacher() : "未配班";
        String beganAt = student.getBeganAt() != null ? student.getBeganAt().toString() : "未开始课程";
        Double score = student.getScore();

        return new StudentDetailInfo(
                name,
                teacher,
                beganAt,
                score
        );
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
}
