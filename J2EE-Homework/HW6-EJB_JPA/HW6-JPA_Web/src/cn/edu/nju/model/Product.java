package cn.edu.nju.model;

import java.io.Serializable;

/**
 * @author hiki on 2017-12-19
 */

public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * product id
     */
    private int id;
    /**
     * product's name
     */
    private String name;
    /**
     * product's price
     */
    private double price;
    /**
     * remaining quantity of the product
     */
    private int quantity;

    public Product(String name, double price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public Product(int id, String name, double price, int quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return this.id + "-" + this.name + "-" + this.price + "-" + this.quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
