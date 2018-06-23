package cn.edu.nju.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author hiki on 2017-12-18
 */

@Entity
@Table(name = "customers")
public class Customer implements Serializable {

    @Transient
    private static final long serialVersionUID = 1L;

    /**
     * customer id
     */
    @Id
    @Column(name = "cid", updatable = false)
    private int id;
    /**
     * password
     */
    @Column(name = "password", nullable = false)
    private String password;
    /**
     * customer's name
     */
    @Column(name = "name", nullable = false)
    private String name;

    public Customer(int id, String password, String name) {
        this.id = id;
        this.password = password;
        this.name = name;
    }

    public Customer() {
    }

    @Override
    public String toString() {
        return this.id + "-" + this.name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
