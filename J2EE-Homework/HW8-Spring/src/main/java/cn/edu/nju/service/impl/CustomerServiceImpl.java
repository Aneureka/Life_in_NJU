package cn.edu.nju.service.impl;


import cn.edu.nju.dao.CustomerDao;
import cn.edu.nju.factory.DaoFactory;
import cn.edu.nju.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @author hiki on 2017-12-20
 */

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerDao customerDao;

    @Override
    public boolean login(int id, String password) {
        return customerDao.login(id, password);
    }

}
