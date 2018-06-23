package cn.edu.nju.TrainingCollege.domain;

import java.util.List;

/**
 * @author hiki on 2018-04-03
 */

public class TrainingPlanPublishForm {

    private String name;

    private String description;

    private Double price;

    private String start;

    private String end;

    private String timePlan;

    private List<TrainingClassPublishForm> classes;

    public TrainingPlanPublishForm() {
    }

    public TrainingPlanPublishForm(String name, String description, Double price, String start, String end, String timePlan, List<TrainingClassPublishForm> classes) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.start = start;
        this.end = end;
        this.timePlan = timePlan;
        this.classes = classes;
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

    public List<TrainingClassPublishForm> getClasses() {
        return classes;
    }

    public void setClasses(List<TrainingClassPublishForm> classes) {
        this.classes = classes;
    }
}
