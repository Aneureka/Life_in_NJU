package cn.edu.nju.service.impl;


import cn.edu.nju.dao.CustomerDao;
import cn.edu.nju.factory.DaoFactory;
import cn.edu.nju.service.CustomerService;


/**
 * @author hiki on 2017-12-20
 */

public class CustomerServiceImpl implements CustomerService {

    private CustomerDao customerDao;

    private static class holder {
        private static final CustomerServiceImpl INSTANCE = new CustomerServiceImpl();
    }

    private CustomerServiceImpl() {
        customerDao = DaoFactory.getCustomerDao();
    }

    public static CustomerServiceImpl getInstance() {
        return holder.INSTANCE;
    }

    @Override
    public boolean login(int id, String password) {
        return customerDao.login(id, password);
    }

}
