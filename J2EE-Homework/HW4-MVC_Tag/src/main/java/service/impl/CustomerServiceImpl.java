package service.impl;

import dao.CustomerDao;
import factory.DaoFactory;
import service.CustomerService;

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

    public static final CustomerServiceImpl getInstance() {
        return holder.INSTANCE;
    }

    @Override
    public boolean login(int id, String password) {
        return customerDao.login(id, password);
    }

}
