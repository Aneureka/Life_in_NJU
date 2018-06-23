package cn.edu.nju.TrainingCollege.domain;

import cn.edu.nju.TrainingCollege.entity.TrainingPlan;

/**
 * @author hiki on 2018-04-05
 */

public class Lesson {

    private Long id;

    private String name;

    private String instituteName;

    private String description;

    private Double price;

    private String start;

    private String end;

    private String timePlan;

    public Lesson() {
    }

    public Lesson(Long id, String name, String instituteName, String description, Double price, String start, String end, String timePlan) {
        this.id = id;
        this.name = name;
        this.instituteName = instituteName;
        this.description = description;
        this.price = price;
        this.start = start;
        this.end = end;
        this.timePlan = timePlan;
    }

    public static Lesson fromTrainingPlan(TrainingPlan plan) {
        return new Lesson(
                plan.getId(),
                plan.getName(),
                plan.getInstitute().getName(),
                plan.getDescription(),
                plan.getPrice(),
                plan.getStart().toString(),
                plan.getEnd().toString(),
                plan.getTimePlan()
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

    public String getTimePlan() {
        return timePlan;
    }

    public void setTimePlan(String timePlan) {
        this.timePlan = timePlan;
    }
}
