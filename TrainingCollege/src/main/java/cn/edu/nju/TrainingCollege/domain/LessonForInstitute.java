package cn.edu.nju.TrainingCollege.domain;

import cn.edu.nju.TrainingCollege.common.constant.OrderStatus;
import cn.edu.nju.TrainingCollege.entity.TrainingOrder;
import cn.edu.nju.TrainingCollege.entity.TrainingPlan;

import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @author hiki on 2018-04-06
 */

public class LessonForInstitute {

    private Long id;

    private String name;

    private String description;

    private Double price;

    private String start;

    private String end;

    private String timePlan;

    private String createdAt;

    private Integer classNumber;

    private Integer paidNumber;

    private Integer withdrawnNumber;

    private Double profit;

    public LessonForInstitute() {
    }

    public LessonForInstitute(Long id, String name, String description, Double price, String start, String end, String timePlan, String createdAt, Integer classNumber, Integer paidNumber, Integer withdrawnNumber, Double profit) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.start = start;
        this.end = end;
        this.timePlan = timePlan;
        this.createdAt = createdAt;
        this.classNumber = classNumber;
        this.paidNumber = paidNumber;
        this.withdrawnNumber = withdrawnNumber;
        this.profit = profit;
    }

    public static LessonForInstitute fromTrainingPlan(TrainingPlan plan) {
        List<TrainingOrder> orders = plan.getTrainingOrders();
        int paidNumber = (int) orders.stream()
                .filter(e -> e.getStatus().equals(OrderStatus.PAID) || e.getStatus().equals(OrderStatus.PAID_UNASSIGNED) || e.getStatus().equals(OrderStatus.PAID_ASSIGNED))
                .count();
        int withdrawnNumber = (int) orders.stream()
                .filter(e -> e.getStatus().equals(OrderStatus.WITHDRAWN))
                .count();

        return new LessonForInstitute(
                plan.getId(),
                plan.getName(),
                plan.getDescription(),
                plan.getPrice(),
                plan.getStart().toString(),
                plan.getEnd().toString(),
                plan.getTimePlan(),
                plan.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss")),
                plan.getTrainingClasses().size(),
                paidNumber,
                withdrawnNumber,
                plan.getProfit()
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getTimePlan() {
        return timePlan;
    }

    public void setTimePlan(String timePlan) {
        this.timePlan = timePlan;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getClassNumber() {
        return classNumber;
    }

    public void setClassNumber(Integer classNumber) {
        this.classNumber = classNumber;
    }

    public Integer getPaidNumber() {
        return paidNumber;
    }

    public void setPaidNumber(Integer paidNumber) {
        this.paidNumber = paidNumber;
    }

    public Integer getWithdrawnNumber() {
        return withdrawnNumber;
    }

    public void setWithdrawnNumber(Integer withdrawnNumber) {
        this.withdrawnNumber = withdrawnNumber;
    }

    public Double getProfit() {
        return profit;
    }

    public void setProfit(Double profit) {
        this.profit = profit;
    }
}
