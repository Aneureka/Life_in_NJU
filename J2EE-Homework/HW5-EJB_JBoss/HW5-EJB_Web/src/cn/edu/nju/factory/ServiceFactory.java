package cn.edu.nju.factory;

import cn.edu.nju.service.CustomerService;
import cn.edu.nju.service.OrderService;
import cn.edu.nju.util.EJBHandler;

/**
 * @author hiki on 2017-12-30
 */

public class ServiceFactory {

    public static CustomerService getCustomerService() {

        return (CustomerService) EJBHandler.getEJB(
                "ejb:/HW5_EJB_Server_war_exploded/CustomerServiceEJB!cn.edu.nju.service.CustomerService");
    }

    public static OrderService getOrderService() {

        return (OrderService) EJBHandler.getEJB(
                "ejb:/HW5_EJB_Server_war_exploded/OrderServiceEJB!cn.edu.nju.service.OrderService");
    }
}
