package cn.edu.nju.TrainingCollege.service;

import cn.edu.nju.TrainingCollege.common.exception.BalanceInsufficientException;
import cn.edu.nju.TrainingCollege.common.exception.ClassAssignFailedException;
import cn.edu.nju.TrainingCollege.domain.OrderDetailInfo;
import cn.edu.nju.TrainingCollege.domain.OrderForLesson;
import cn.edu.nju.TrainingCollege.domain.TrainingOrderForm;
import cn.edu.nju.TrainingCollege.domain.TrainingOrderInfo;
import cn.edu.nju.TrainingCollege.entity.TrainingOrder;
import cn.edu.nju.TrainingCollege.entity.User;

import java.util.List;

/**
 * @author hiki on 2018-03-22
 */

public interface TrainingOrderService {

    TrainingOrder create(User user, TrainingOrderForm form) throws ClassAssignFailedException;

    TrainingOrder pay(TrainingOrder order) throws BalanceInsufficientException;

    TrainingOrder modify(TrainingOrder order);

    TrainingOrder withdraw(TrainingOrder order);

    List<OrderForLesson> getOrdersForLesson(Long id);

    OrderDetailInfo getOrderDetailInfo(Long id);

    TrainingOrder findOne(Long id);

    List<TrainingOrderInfo> findByUser(Long id);

    void remove(Long id);

    Integer getOrderNumber();

}
