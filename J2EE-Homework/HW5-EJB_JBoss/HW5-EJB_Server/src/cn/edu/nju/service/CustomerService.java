package cn.edu.nju.service;

import javax.ejb.Remote;

/**
 * @author hiki on 2017-12-30
 */

@Remote
public interface CustomerService {

    boolean login(int id, String password);
}
