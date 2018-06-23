package cn.edu.nju.TrainingCollege.domain;

/**
 * @author hiki on 2018-04-06
 */

public class StudentModifyForm {

    private Long studentId;

    private String beganAt;

    private Double score;

    public StudentModifyForm() {
    }

    public StudentModifyForm(Long studentId, String beganAt, Double score) {
        this.studentId = studentId;
        this.beganAt = beganAt;
        this.score = score;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
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
