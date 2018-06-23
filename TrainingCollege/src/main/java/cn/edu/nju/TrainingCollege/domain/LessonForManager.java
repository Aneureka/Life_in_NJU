package cn.edu.nju.TrainingCollege.domain;

import cn.edu.nju.TrainingCollege.entity.TrainingOrder;
import cn.edu.nju.TrainingCollege.entity.TrainingPlan;

import java.util.List;

/**
 * @author hiki on 2018-04-07
 */

public class LessonForManager {

    private Long id;

    private String lessonName;

    private String instituteName;

    private String description;

    private Double price;

    private String start;

    private String end;

    private Double profit;

    public LessonForManager() {
    }

    public LessonForManager(Long id, String lessonName, String instituteName, String description, Double price, String start, String end, Double profit) {
        this.id = id;
        this.lessonName = lessonName;
        this.instituteName = instituteName;
        this.description = description;
        this.price = price;
        this.start = start;
        this.end = end;
        this.profit = profit;
    }

    public static LessonForManager fromTrainingPlan(TrainingPlan plan) {
        List<TrainingOrder> orders = plan.getTrainingOrders();
        return new LessonForManager(
                plan.getId(),
                plan.getName(),
                plan.getInstitute().getName(),
                plan.getDescription(),
                plan.getPrice(),
                plan.getStart().toString(),
                plan.getEnd().toString(),
                plan.getProfit()
        );
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLessonName() {
        return lessonName;
    }

    public void setLessonName(String lessonName) {
        this.lessonName = lessonName;
    }

    public String getInstituteName() {
        return instituteName;
    }

    public void setInstituteName(String instituteName) {
        this.instituteName = instituteName;
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

    public Double getProfit() {
        return profit;
    }

    public void setProfit(Double profit) {
        this.profit = profit;
    }
}
