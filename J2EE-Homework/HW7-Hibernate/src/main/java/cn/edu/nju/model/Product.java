package cn.edu.nju.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author hiki on 2017-12-19
 */

@Entity
@Table(name = "products")
public class Product implements Serializable {

    @Transient
    private static final long serialVersionUID = 1L;

    /**
     * product id
     */
    @Id
    @Column(name = "pid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    /**
     * product's name
     */
    @Column(name = "name", nullable = false)
    private String name;
    /**
     * product's price
     */
    @Column(name = "price", nullable = false)
    private double price;
    /**
     * remaining quantity of the product
     */
    @Column(name = "quantity", nullable = false)
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

    public Product() {
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
