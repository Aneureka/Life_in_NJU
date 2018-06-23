package cn.edu.nju.TrainingCollege.entity;

import cn.edu.nju.TrainingCollege.domain.TrainingClassPublishForm;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author hiki on 2018-01-29
 */

@Entity
public class TrainingClass {
    /**
     * training class's id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * the plan that this class belongs with
     */
    @ManyToOne
    @JoinColumn(name = "plan_id")
    private TrainingPlan trainingPlan;
    /**
     * teacher responsible for this class
     */
    @Column(nullable = false)
    private String teacher;
    /**
     * number of the student this class can accommodate
     */
    @Column(nullable = false)
    private Integer capacity;
    /**
     * number of the existing students
     */
    private Integer occupancy;
    /**
     * students in this class
     */
    @OneToMany(mappedBy = "trainingClass")
    private List<Student> students;

    public TrainingClass() {
    }

    public TrainingClass(TrainingPlan trainingPlan, String teacher, Integer capacity, List<Student> students) {
        this.trainingPlan = trainingPlan;
        this.teacher = teacher;
        this.capacity = capacity;
        this.occupancy = 0;
        this.students = students;
    }

    private TrainingClass(TrainingPlan trainingPlan, String teacher, Integer capacity) {
        this.trainingPlan = trainingPlan;
        this.teacher = teacher;
        this.capacity = capacity;
        this.occupancy = 0;
        this.students = new ArrayList<>();
    }

    public static TrainingClass fromPlanAndPublishForm(TrainingPlan plan, TrainingClassPublishForm form) {
        return new TrainingClass(
                plan,
                form.getTeacher(),
                form.getCapacity()
        );
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TrainingPlan getTrainingPlan() {
        return trainingPlan;
    }

    public void setTrainingPlan(TrainingPlan trainingPlan) {
        this.trainingPlan = trainingPlan;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public Integer getOccupancy() {
        return occupancy;
    }

    public void setOccupancy(Integer occupancy) {
        this.occupancy = occupancy;
    }
}
