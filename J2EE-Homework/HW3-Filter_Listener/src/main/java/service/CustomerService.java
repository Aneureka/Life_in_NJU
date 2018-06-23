package service;

import repository.CustomerRepository;

/**
 * @author hiki on 2017-12-20
 */

public class CustomerService {

    private CustomerRepository cr;

    public CustomerService() {
        cr = new CustomerRepository();
    }

    public boolean login(int id, String password) {
        return cr.login(id, password);
    }

    public boolean isExisted(int id) {
        return cr.isExisted(id);
    }
}
