package test;

import cn.edu.nju.dao.CustomerDao;
import cn.edu.nju.factory.DaoFactory;

/**
 * @author hiki on 2018-01-05
 */

public class DaoTest {

    public static void main(String[] args) {

        CustomerDao dao = DaoFactory.getCustomerDao();

        System.out.println(dao.findAll());
    }
}
