package cn.edu.nju.dao.impl;


import cn.edu.nju.dao.CustomerDao;
import cn.edu.nju.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author hiki on 2017-12-18
 */

@Repository
@Transactional
public class CustomerDaoImpl implements CustomerDao {

    @Autowired
    private AbstractDao<Customer> dao;

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

