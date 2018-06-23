package cn.edu.nju.factory;


import cn.edu.nju.dao.CustomerDao;
import cn.edu.nju.dao.OrderDao;
import cn.edu.nju.dao.ProductDao;
import cn.edu.nju.dao.impl.CustomerDaoImpl;
import cn.edu.nju.dao.impl.OrderDaoImpl;
import cn.edu.nju.dao.impl.ProductDaoImpl;

/**
 * @author hiki on 2017-12-30
 */

public class DaoFactory {

    public static CustomerDao getCustomerDao() {
        return CustomerDaoImpl.getInstance();
    }

    public static OrderDao getOrderDao() {
        return OrderDaoImpl.getInstance();
    }

    public static ProductDao getProductDao() {
        return ProductDaoImpl.getInstance();
    }

}
