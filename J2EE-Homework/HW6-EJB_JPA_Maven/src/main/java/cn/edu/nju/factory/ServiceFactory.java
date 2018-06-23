package cn.edu.nju.factory;

import cn.edu.nju.service.CustomerService;
import cn.edu.nju.service.OrderService;
import cn.edu.nju.service.impl.CustomerServiceImpl;
import cn.edu.nju.service.impl.OrderServiceImpl;

/**
 * @author hiki on 2017-12-30
 */

public class ServiceFactory {

    public static CustomerService getCustomerService() {
        return CustomerServiceImpl.getInstance();
    }

    public static OrderService getOrderService() {
        return OrderServiceImpl.getInstance();
    }
}
