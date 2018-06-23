package cn.edu.nju.TrainingCollege.domain;

import cn.edu.nju.TrainingCollege.entity.TrainingOrder;

import java.time.format.DateTimeFormatter;

/**
 * @author hiki on 2018-04-06
 */

public class TrainingOrderInfo {

    private Long orderId;

    private String lessonName;

    private Double price;

    private Double totalPrice;

    private String createdAt;

    private String orderStatus;

    public TrainingOrderInfo() {
    }

    public TrainingOrderInfo(Long orderId, String lessonName, Double price, Double totalPrice, String createdAt, String orderStatus) {
        this.orderId = orderId;
        this.lessonName = lessonName;
        this.price = price;
        this.totalPrice = totalPrice;
        this.createdAt = createdAt;
        this.orderStatus = orderStatus;
    }

    public static TrainingOrderInfo fromTrainingOrder(TrainingOrder order) {
        return new TrainingOrderInfo(
                order.getId(),
                order.getTrainingPlan().getName(),
                order.getTrainingPlan().getPrice(),
                order.getTotalPrice(),
                order.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss")),
                order.getStatus().toString()
        );
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getLessonName() {
        return lessonName;
    }

    public void setLessonName(String lessonName) {
        this.lessonName = lessonName;
    }


    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
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
}
