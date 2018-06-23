package cn.edu.nju.service;

import org.springframework.stereotype.Service;

/**
 * @author hiki on 2017-12-30
 */

@Service
public interface CustomerService {

    boolean login(int id, String password);
}
