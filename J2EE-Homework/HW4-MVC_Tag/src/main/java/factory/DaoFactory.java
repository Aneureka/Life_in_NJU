package factory;

import dao.CustomerDao;
import dao.OrderDao;
import dao.ProductDao;
import dao.impl.CustomerDaoImpl;
import dao.impl.OrderDaoImpl;
import dao.impl.ProductDaoImpl;

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
