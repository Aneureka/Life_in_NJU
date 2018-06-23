package cn.edu.nju.dao.impl;


import cn.edu.nju.dao.CustomerDao;
import cn.edu.nju.model.Customer;

import java.util.List;

/**
 * @author hiki on 2017-12-18
 */

public class CustomerDaoImpl implements CustomerDao {

    private AbstractDao<Customer> dao;

    private static class holder {
        private static final CustomerDaoImpl INSTANCE = new CustomerDaoImpl();
    }

    private CustomerDaoImpl() {
        dao = new AbstractDao<>(Customer.class);
    }

    public static CustomerDaoImpl getInstance() {
        return holder.INSTANCE;
    }

    /*========================================== method ===============================================*/

    @Override
    public List<Customer> findAll() {
        return dao.findAll();
    }

    @Override
    public Customer findByCustomerId(int id) {
        return dao.findOne(id);
    }

    @Override
    public boolean login(int id, String password) {
        String query = "from " + Customer.class.getName() + " c where c.id=" + id + " and c.password=" + password;
        return dao.findOneByQuery(query) != null;
    }

    @Override
    public boolean add(Customer customer) {
        return dao.create(customer);
    }

    @Override
    public boolean isExisted(int id){
        return dao.findOne(id) != null;
    }

}

