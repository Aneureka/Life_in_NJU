package cn.edu.nju.model.vo;

import cn.edu.nju.dao.CustomerDao;
import cn.edu.nju.dao.ProductDao;
import cn.edu.nju.factory.DaoFactory;
import cn.edu.nju.model.Customer;
import cn.edu.nju.model.Order;
import cn.edu.nju.model.Product;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * The order value to display
 * @author hiki on 2017-12-19
 */

public class OrderVO implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * order id
     */
    private int id;
    /**
     * customer's name
     */
    private String customerName;
    /**
     * product's name
     */
    private String productName;
    /**
     * number of products
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

    public OrderVO(int id, String customerName, String productName, int quantity, double totalPrice, LocalDateTime createdAt) {
        this.id = id;
        this.customerName = customerName;
        this.productName = productName;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.createdAt = createdAt;
    }


    @Override
    public String toString() {
        return id + "-" + customerName + "-" + productName + "-" + quantity + "-" + totalPrice + "-" + createdAt.toString();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
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
