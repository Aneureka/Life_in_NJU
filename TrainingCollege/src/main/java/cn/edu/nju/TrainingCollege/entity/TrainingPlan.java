package cn.edu.nju.TrainingCollege.entity;

import cn.edu.nju.TrainingCollege.domain.TrainingPlanPublishForm;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author hiki on 2018-01-29
 */

@Entity
public class TrainingPlan {

    /**
     * training plan's id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * in which institute
     */
    @ManyToOne
    @JoinColumn(name = "institute_id")
    private Institute institute;
    /**
     * name of the lesson
     */
    @Column(nullable = false)
    private String name;
    /**
     * brief description of the course
     */
    @Column(columnDefinition = "longtext")
    private String description;
    /**
     * price of the training course
     */
    @Column(nullable = false)
    private Double price;
    /**
     * time to begin the lesson
     */
    @Column(nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate start;
    /**
     * time to end the lesson
     */
    @Column(nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate end;
    /**
     * time plan of the training course
     */
    @Column(nullable = false)
    private String timePlan;
    /**
     * profit
     */
    private Double profit;
    /**
     * creating time
     */
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime createdAt;
    /**
     * classes equipped for the training plan
     */
    @OneToMany(mappedBy = "trainingPlan", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TrainingClass> trainingClasses;
    /**
     * orders for this plan
     */
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "trainingPlan", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TrainingOrder> trainingOrders;

    public TrainingPlan() {
    }

    private TrainingPlan(Institute institute, String name, String description, Double price, LocalDate start, LocalDate end, String timePlan) {
        this.institute = institute;
        this.name = name;
        this.description = description;
        this.price = price;
        this.start = start;
        this.end = end;
        this.timePlan = timePlan;
        this.profit = 0.0;
        this.createdAt = LocalDateTime.now();
        this.trainingClasses = new ArrayList<>();
        this.trainingOrders = new ArrayList<>();
    }

    public static TrainingPlan fromInstituteAndPublishForm(Institute institute, TrainingPlanPublishForm form) {
        return new TrainingPlan(
                institute,
                form.getName(),
                form.getDescription(),
                form.getPrice(),
                LocalDate.parse(form.getStart(), DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                LocalDate.parse(form.getEnd(), DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                form.getTimePlan()
        );
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Institute getInstitute() {
        return institute;
    }

    public void setInstitute(Institute institute) {
        this.institute = institute;
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

    public LocalDate getStart() {
        return start;
    }

    public void setStart(LocalDate start) {
        this.start = start;
    }

    public LocalDate getEnd() {
        return end;
    }

    public void setEnd(LocalDate end) {
        this.end = end;
    }

    public String getTimePlan() {
        return timePlan;
    }

    public void setTimePlan(String timePlan) {
        this.timePlan = timePlan;
    }

    public Double getProfit() {
        return profit;
    }

    public void setProfit(Double profit) {
        this.profit = profit;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<TrainingClass> getTrainingClasses() {
        return trainingClasses;
    }

    public void setTrainingClasses(List<TrainingClass> trainingClasses) {
        this.trainingClasses = trainingClasses;
    }

    public List<TrainingOrder> getTrainingOrders() {
        return trainingOrders;
    }

    public void setTrainingOrders(List<TrainingOrder> trainingOrders) {
        this.trainingOrders = trainingOrders;
    }
}
