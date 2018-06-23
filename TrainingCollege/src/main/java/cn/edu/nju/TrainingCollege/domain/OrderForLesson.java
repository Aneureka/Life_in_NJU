package cn.edu.nju.TrainingCollege.domain;

import cn.edu.nju.TrainingCollege.entity.TrainingOrder;

import java.time.format.DateTimeFormatter;

/**
 * @author hiki on 2018-04-06
 */

public class OrderForLesson {

    private Long id;

    private String username;

    private Integer number;

    private Double totalPrice;

    private String createdAt;

    private String orderStatus;

    public OrderForLesson() {
    }

    public OrderForLesson(Long id, String username, Integer number, Double totalPrice, String createdAt, String orderStatus) {
        this.id = id;
        this.username = username;
        this.number = number;
        this.totalPrice = totalPrice;
        this.createdAt = createdAt;
        this.orderStatus = orderStatus;
    }

    public static OrderForLesson fromOrder(TrainingOrder order) {
        return new OrderForLesson(
                order.getId(),
                order.getUser().getName(),
                order.getStudents().size(),
                order.getTotalPrice(),
                order.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss")),
                order.getStatus().toString()
        );
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
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
