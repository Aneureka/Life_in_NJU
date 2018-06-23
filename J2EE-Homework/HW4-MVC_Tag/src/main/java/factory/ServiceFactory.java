package factory;

import service.CustomerService;
import service.OrderService;
import service.impl.CustomerServiceImpl;
import service.impl.OrderServiceImpl;

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
