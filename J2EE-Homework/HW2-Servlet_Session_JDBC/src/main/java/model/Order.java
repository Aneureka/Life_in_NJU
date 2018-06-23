package model;

import java.time.LocalDateTime;

/**
 * @author hiki on 2017-12-18
 */

public class Order {

    /**
     * page size used for paging
     */
    public static final int PAGE_SIZE = 22;
    /**
     * order id
     */
    private int id;
    /**
     * customer id
     */
    private int customerId;
    /**
     * product id
     */
    private int productId;
    /**
     * quantity
     */
    private int quantity;
    /**
     * total price
     */
    private double totalPrice;
    /**
     * order creating time
     */
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
