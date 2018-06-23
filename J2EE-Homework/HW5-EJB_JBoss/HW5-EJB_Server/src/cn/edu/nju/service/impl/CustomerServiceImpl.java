package cn.edu.nju.service.impl;


import cn.edu.nju.dao.CustomerDao;
import cn.edu.nju.factory.DaoFactory;
import cn.edu.nju.service.CustomerService;

import javax.ejb.Stateless;

/**
 * @author hiki on 2017-12-20
 */

@Stateless(name = "CustomerServiceEJB")
public class CustomerServiceImpl implements CustomerService {

    private CustomerDao customerDao;

    public CustomerServiceImpl() {
        customerDao = DaoFactory.getCustomerDao();
    }

    @Override
    public boolean login(int id, String password) {
        return customerDao.login(id, password);
    }

}
