package cn.edu.nju.service;


import cn.edu.nju.model.vo.OrderVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author hiki on 2017-12-30
 */

@Service
public interface OrderService {

    List<OrderVO> findOrderVOByCustomerId(int customerId, int page);

    int findCount(int customerId);
}
