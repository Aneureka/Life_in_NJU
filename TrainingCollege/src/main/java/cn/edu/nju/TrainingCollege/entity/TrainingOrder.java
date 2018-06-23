package cn.edu.nju.TrainingCollege.entity;

import cn.edu.nju.TrainingCollege.common.constant.OrderStatus;
import cn.edu.nju.TrainingCollege.common.exception.ClassAssignFailedException;
import cn.edu.nju.TrainingCollege.domain.TrainingOrderForm;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author hiki on 2018-01-29
 */

@Entity
public class TrainingOrder {

    /**
     * training order's id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * created by whom
     */
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;
    /**
     * whether the user want to select class
     */
    @Column(nullable = false)
    private Boolean selectingClass;
    /**
     * students in the order
     */
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "trainingOrder", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Student> students;
    /**
     * related plan
     */
    @ManyToOne
    @JoinColumn(name = "plan_id")
    @JsonIgnore
    private TrainingPlan trainingPlan;
    /**
     * original total price for all the ordered class
     */
    @Column(nullable = false)
    private Double originalTotalPrice;
    /**
     * total price for all the ordered classes
     */
    @Column(nullable = false)
    private Double totalPrice;
    /**
     * order's status
     */
    @Column(nullable = false)
    private OrderStatus status;
    /**
     * creating time
     */
    @Column(nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    public TrainingOrder() {
    }

    public TrainingOrder(User user, Boolean selectingClass, List<Student> students, TrainingPlan trainingPlan, Double originalTotalPrice, Double totalPrice) {
        this.user = user;
        this.selectingClass = selectingClass;
        this.students = students;
        this.trainingPlan = trainingPlan;
        this.originalTotalPrice = originalTotalPrice;
        this.totalPrice = totalPrice;
        this.status = OrderStatus.UNPAID;
        this.createdAt = LocalDateTime.now();
    }

    public static TrainingOrder fromUserAndPlanAndForm(User user, TrainingPlan plan, TrainingOrderForm form) throws ClassAssignFailedException {

        int studentNum = form.getStudentNames().size();
        double originalTotalPrice = studentNum * plan.getPrice();
        double totalPrice = originalTotalPrice * (1 - 0.05*user.getRate());

        // check if the classes are successful to assign
        List<TrainingClass> classes = plan.getTrainingClasses();
        String teacher = form.getSelectingClass();
        boolean selectingClass = false;
        TrainingClass selectedClass = null;
        for (TrainingClass tc : classes) {
            String t = tc.getTeacher();
            if (t.equals(teacher)) {
                selectingClass = true;
                selectedClass = tc;
                break;
            }
        }

        List<Student> students = new ArrayList<>();
        for (String student : form.getStudentNames()) {
            students.add(new Student(student));
        }

        if (selectingClass) {
            if (selectedClass.getCapacity() - selectedClass.getOccupancy() < studentNum) {
                throw new ClassAssignFailedException();
            }
            selectedClass.setOccupancy(selectedClass.getOccupancy() + studentNum);
            for (Student s : students) {
                s.setTrainingClass(selectedClass);
            }
        }

        return new TrainingOrder(
                user,
                selectingClass,
                students,
                plan,
                originalTotalPrice,
                totalPrice
        );

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Boolean getSelectingClass() {
        return selectingClass;
    }

    public void setSelectingClass(Boolean selectingClass) {
        this.selectingClass = selectingClass;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public TrainingPlan getTrainingPlan() {
        return trainingPlan;
    }

    public void setTrainingPlan(TrainingPlan trainingPlan) {
        this.trainingPlan = trainingPlan;
    }

    public Double getOriginalTotalPrice() {
        return originalTotalPrice;
    }

    public void setOriginalTotalPrice(Double originalTotalPrice) {
        this.originalTotalPrice = originalTotalPrice;
    }
}
