package cn.edu.nju.TrainingCollege.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author hiki on 2018-01-30
 */

@Entity
public class Manager {

    /**
     * manager's unique id
     */
    @Id
    @Column(nullable = false)
    private Long id;
    /**
     * password
     */
    @Column(nullable = false)
    private String password;
    /**
     * total profit of training college
     */
    private Double totalProfit;

    public Manager() {
    }

    public Manager(Long id, String password) {
        this.id = id;
        this.password = password;
        this.totalProfit = 0.0;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Double getTotalProfit() {
        return totalProfit;
    }

    public void setTotalProfit(Double totalProfit) {
        this.totalProfit = totalProfit;
    }
}
