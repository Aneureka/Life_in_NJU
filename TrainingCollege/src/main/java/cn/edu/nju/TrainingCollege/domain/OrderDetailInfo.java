package cn.edu.nju.TrainingCollege.domain;

import cn.edu.nju.TrainingCollege.entity.TrainingOrder;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author hiki on 2018-04-07
 */

public class OrderDetailInfo {

    private String instituteName;

    private String lessonName;

    private String description;

    private Double originalTotalPrice;

    private Double totalPrice;

    private String createdAt;

    private String orderStatus;

    private List<StudentDetailInfo> studentDetailInfos;

    public OrderDetailInfo() {
    }

    public OrderDetailInfo(String instituteName, String lessonName, String description, Double originalTotalPrice, Double totalPrice, String createdAt, String orderStatus, List<StudentDetailInfo> studentDetailInfos) {
        this.instituteName = instituteName;
        this.lessonName = lessonName;
        this.description = description;
        this.originalTotalPrice = originalTotalPrice;
        this.totalPrice = totalPrice;
        this.createdAt = createdAt;
        this.orderStatus = orderStatus;
        this.studentDetailInfos = studentDetailInfos;
    }

    public static OrderDetailInfo fromOrder(TrainingOrder order) {
        return new OrderDetailInfo(
                order.getTrainingPlan().getInstitute().getName(),
                order.getTrainingPlan().getName(),
                order.getTrainingPlan().getDescription(),
                order.getOriginalTotalPrice(),
                order.getTotalPrice(),
                order.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss")),
                order.getStatus().toString(),
                order.getStudents().stream().map(StudentDetailInfo::fromStudent).collect(Collectors.toList())
        );
    }

    public String getInstituteName() {
        return instituteName;
    }

    public void setInstituteName(String instituteName) {
        this.instituteName = instituteName;
    }

    public String getLessonName() {
        return lessonName;
    }

    public void setLessonName(String lessonName) {
        this.lessonName = lessonName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getOriginalTotalPrice() {
        return originalTotalPrice;
    }

    public void setOriginalTotalPrice(Double originalTotalPrice) {
        this.originalTotalPrice = originalTotalPrice;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public List<StudentDetailInfo> getStudentDetailInfos() {
        return studentDetailInfos;
    }

    public void setStudentDetailInfos(List<StudentDetailInfo> studentDetailInfos) {
        this.studentDetailInfos = studentDetailInfos;
    }
}
