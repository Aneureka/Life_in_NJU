package cn.edu.nju.TrainingCollege.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author hiki on 2018-01-29
 * This [student] doesn't stand for a individual. It's a logic concept for a class.
 */

@Entity
public class Student {

    /**
     * student's unique id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * the class that this student in
     */
    @ManyToOne
    @JoinColumn(name = "training_class_id")
    @JsonIgnore
    private TrainingClass trainingClass;
    /**
     * the order that this student belongs with
     */
    @ManyToOne
    @JoinColumn(name = "training_order_id")
    @JsonIgnore
    private TrainingOrder trainingOrder;
    /**
     * name
     */
    @Column(nullable = false)
    private String name;
    /**
     * the time that the student created for the class
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    /**
     * the first time that the student joins the class
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate beganAt;
    /**
     * the score that the student gets in the lesson
     */
    private Double score;
    /**
     * whether the student is quit
     */
    private Boolean quit;

    public Student() {
    }

    public Student(TrainingClass trainingClass, TrainingOrder trainingOrder, String name, LocalDateTime createdAt, LocalDate beganAt, Double score, Boolean quit) {
        this.trainingClass = trainingClass;
        this.trainingOrder = trainingOrder;
        this.name = name;
        this.createdAt = createdAt;
        this.beganAt = beganAt;
        this.score = score;
        this.quit = quit;
    }

    public Student(TrainingClass trainingClass, TrainingOrder trainingOrder, String name) {
        this.trainingClass = trainingClass;
        this.trainingOrder = trainingOrder;
        this.name = name;
        this.createdAt = LocalDateTime.now();
        this.score = -1.0;
        this.quit = false;
    }

    public Student(TrainingClass trainingClass, String name) {
        this.trainingClass = trainingClass;
        this.trainingOrder = null;
        this.name = name;
        this.createdAt = LocalDateTime.now();
        this.score = -1.0;
        this.quit = false;
    }

    public Student(String name) {
        this.trainingClass = null;
        this.trainingOrder = null;
        this.name = name;
        this.createdAt = LocalDateTime.now();
        this.score = -1.0;
        this.quit = false;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TrainingClass getTrainingClass() {
        return trainingClass;
    }

    public void setTrainingClass(TrainingClass trainingClass) {
        this.trainingClass = trainingClass;
    }

    public TrainingOrder getTrainingOrder() {
        return trainingOrder;
    }

    public void setTrainingOrder(TrainingOrder trainingOrder) {
        this.trainingOrder = trainingOrder;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDate getBeganAt() {
        return beganAt;
    }

    public void setBeganAt(LocalDate beganAt) {
        this.beganAt = beganAt;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Boolean getQuit() {
        return quit;
    }

    public void setQuit(Boolean quit) {
        this.quit = quit;
    }
}
