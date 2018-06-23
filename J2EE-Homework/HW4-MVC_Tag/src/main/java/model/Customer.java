package model;

/**
 * @author hiki on 2017-12-18
 */

public class Customer {
    /**
     * customer id
     */
    private int id;
    /**
     * password
     */
    private String password;
    /**
     * customer's name
     */
    private String name;

    public Customer(int id, String password, String name) {
        this.id = id;
        this.password = password;
        this.name = name;
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
