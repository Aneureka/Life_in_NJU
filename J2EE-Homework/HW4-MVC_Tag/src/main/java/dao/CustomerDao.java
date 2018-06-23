package dao;

import model.Customer;

import java.util.List;

/**
 * @author hiki on 2017-12-30
 */

public interface CustomerDao {

    List<Customer> findAll();

    Customer findByCustomerId(int id);

    boolean login(int id, String password);

    boolean add(Customer customer);

    boolean isExisted(int id);

}
