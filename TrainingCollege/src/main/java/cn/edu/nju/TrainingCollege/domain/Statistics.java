package cn.edu.nju.TrainingCollege.domain;

/**
 * @author hiki on 2018-04-08
 */

public class Statistics {

    private Double totalProfit;

    private Integer userNumber;

    private Integer instituteNumber;

    private Integer lessonNumber;

    private Integer orderNumber;

    private Integer studentNumber;

    public Statistics() {
    }

    public Statistics(Double totalProfit, Integer userNumber, Integer instituteNumber, Integer lessonNumber, Integer orderNumber, Integer studentNumber) {
        this.totalProfit = totalProfit;
        this.userNumber = userNumber;
        this.instituteNumber = instituteNumber;
        this.lessonNumber = lessonNumber;
        this.orderNumber = orderNumber;
        this.studentNumber = studentNumber;
    }

    public Double getTotalProfit() {
        return totalProfit;
    }

    public void setTotalProfit(Double totalProfit) {
        this.totalProfit = totalProfit;
    }

    public Integer getUserNumber() {
        return userNumber;
    }

    public void setUserNumber(Integer userNumber) {
        this.userNumber = userNumber;
    }

    public Integer getInstituteNumber() {
        return instituteNumber;
    }

    public void setInstituteNumber(Integer instituteNumber) {
        this.instituteNumber = instituteNumber;
    }

    public Integer getLessonNumber() {
        return lessonNumber;
    }

    public void setLessonNumber(Integer lessonNumber) {
        this.lessonNumber = lessonNumber;
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Integer getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(Integer studentNumber) {
        this.studentNumber = studentNumber;
    }
}
