package cn.edu.nju.model;

import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author hiki on 2017-12-18
 */

@Entity
@Table(name = "orders")
public class Order implements Serializable {

    @Transient
    private static final long serialVersionUID = 1L;

    /**
     * order id
     */
    @Id
    @Column(name = "oid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    /**
     * customer id
     */
    @Column(name = "cid", nullable = false)
    private int customerId;
    /**
     * product id
     */
    @Column(name = "pid", nullable = false)
    private int productId;
    /**
     * quantity
     */
    @Column(name = "quantity", nullable = false)
    private int quantity;
    /**
     * total price
     */
    @Column(name = "total_price", nullable = false)
    private double totalPrice;
    /**
     * order creating time
     */
    @Column(name = "created_at")
    @Generated(GenerationTime.INSERT)
    private LocalDateTime createdAt;

    public Order(int customerId, int productId, int quantity) {
        this.customerId = customerId;
        this.productId = productId;
        this.quantity = quantity;
        this.totalPrice = 0.0;
    }

    public Order(int customerId, int productId, int quantity, double totalPrice) {
        this.customerId = customerId;
        this.productId = productId;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }

    public Order(int id, int customerId, int productId, int quantity, double totalPrice, LocalDateTime createdAt) {
        this.id = id;
        this.customerId = customerId;
        this.productId = productId;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.createdAt = createdAt;
    }

    public Order() {
    }

    @Override
    public String toString() {
        return this.id + "-" + this.customerId + "-" + this.productId + "-" + this.quantity + "-" + this.totalPrice + "-" + this.createdAt.toString();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

}
