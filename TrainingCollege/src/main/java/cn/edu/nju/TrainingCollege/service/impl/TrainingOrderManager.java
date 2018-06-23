package cn.edu.nju.TrainingCollege.service.impl;

import cn.edu.nju.TrainingCollege.common.constant.OrderStatus;
import cn.edu.nju.TrainingCollege.common.exception.BalanceInsufficientException;
import cn.edu.nju.TrainingCollege.common.exception.ClassAssignFailedException;
import cn.edu.nju.TrainingCollege.domain.OrderDetailInfo;
import cn.edu.nju.TrainingCollege.domain.OrderForLesson;
import cn.edu.nju.TrainingCollege.domain.TrainingOrderForm;
import cn.edu.nju.TrainingCollege.domain.TrainingOrderInfo;
import cn.edu.nju.TrainingCollege.entity.*;
import cn.edu.nju.TrainingCollege.repository.*;
import cn.edu.nju.TrainingCollege.service.TrainingOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author hiki on 2018-03-22
 */
@Service
public class TrainingOrderManager implements TrainingOrderService {

    @Autowired
    private TrainingOrderRepository repository;

    @Autowired
    private TrainingPlanRepository trainingPlanRepository;

    @Autowired
    private TrainingClassRepository trainingClassRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StudentRepository studentRepository;


    @Override
    @Transactional
    public TrainingOrder create(User user, TrainingOrderForm form) throws ClassAssignFailedException {

        // create order
        TrainingPlan plan = trainingPlanRepository.findOne(form.getLessonId());
        TrainingOrder order = TrainingOrder.fromUserAndPlanAndForm(user, plan, form);

        userRepository.save(user);
        trainingPlanRepository.save(plan);
        TrainingOrder res = repository.save(order);
        List<Student> students = res.getStudents();
        students.forEach(e -> e.setTrainingOrder(res));
        studentRepository.save(students);

        return res;

    }

    @Override
    @Transactional
    public TrainingOrder pay(TrainingOrder order) throws BalanceInsufficientException {

        User user = order.getUser();
        double totalPrice = order.getTotalPrice();
        if (totalPrice > user.getBalance()) {
            throw new BalanceInsufficientException();
        }

        // deduction and save
        user.setBalance(user.getBalance() - totalPrice);
        user.setCredit(user.getCredit() + (int) totalPrice);
        if (order.getOriginalTotalPrice() > 500) {
            if (user.getRate() < 5) {
                user.setRate(user.getRate() + 1);
            }
        }
        userRepository.save(user);

        TrainingPlan plan = order.getTrainingPlan();
        plan.setProfit(plan.getProfit() + totalPrice);
        trainingPlanRepository.save(plan);

        if (order.getSelectingClass())
            order.setStatus(OrderStatus.PAID);
        else
            order.setStatus(OrderStatus.PAID_UNASSIGNED);
        return repository.save(order);

    }

    @Override
    public TrainingOrder modify(TrainingOrder order) {
        return repository.save(order);
    }

    @Override
    public TrainingOrder withdraw(TrainingOrder order) {
        LocalDate now = LocalDate.now();
        LocalDate start = order.getTrainingPlan().getStart();
        User user = order.getUser();
        TrainingPlan plan = order.getTrainingPlan();
        if (now.isAfter(start)) {
            order.setStatus(OrderStatus.WITHDRAWN);
        }
        else {
            double totalPrice = order.getTotalPrice();
            // refund
            if (now.plusDays(2*7).isAfter(start)) {
                double refund = totalPrice * 0.5;
                user.setBalance(user.getBalance() + refund);
                plan.setProfit(plan.getProfit() - refund);
            }
            else {
                double refund = totalPrice * 0.8;
                user.setBalance(user.getBalance() + refund);
                plan.setProfit(plan.getProfit() - refund);
            }
            order.setStatus(OrderStatus.WITHDRAWN);
        }
        return repository.save(order);
    }

    @Override
    public List<OrderForLesson> getOrdersForLesson(Long id) {
        TrainingPlan plan = trainingPlanRepository.findOne(id);
        return plan.getTrainingOrders()
                .stream()
                .map(OrderForLesson::fromOrder)
                .collect(Collectors.toList());
    }

    @Override
    public OrderDetailInfo getOrderDetailInfo(Long id) {
        return OrderDetailInfo.fromOrder(repository.findOne(id));
    }

    @Override
    public TrainingOrder findOne(Long id) {
        return repository.findOne(id);
    }


    @Override
    public List<TrainingOrderInfo> findByUser(Long id) {
        User user = userRepository.findOne(id);
        return repository.findByUser(user).stream().map(TrainingOrderInfo::fromTrainingOrder).collect(Collectors.toList());
    }

    @Override
    public void remove(Long id) {
        repository.delete(id);
    }

    @Override
    public Integer getOrderNumber() {
        return repository.findAll().size();
    }


    @Scheduled(fixedRate = 1000*60)
    public void autoCancelUnpaidOrders() {
        // find all unpaid orders
        List<TrainingPlan> plans = trainingPlanRepository.findAll()
                .stream()
                .filter(e -> e.getStart().isAfter(LocalDate.now()))
                .collect(Collectors.toList());
        if (!plans.isEmpty()) {
            // set cancel status to unpaid orders
            for (TrainingPlan plan : plans) {
                LocalDateTime now = LocalDateTime.now();
                List<TrainingOrder> orders = plan.getTrainingOrders();
                if (!orders.isEmpty()) {
                    for (TrainingOrder order : orders) {
                        if (order.getStatus().equals(OrderStatus.UNPAID) && Duration.between(order.getCreatedAt(), now).toMinutes() > 15) {
                            order.setStatus(OrderStatus.CANCEL);
                            order.getStudents().clear();
                        }
                    }
                }
            }
            trainingPlanRepository.save(plans);
        }
    }

    @Scheduled(fixedRate = 1000*60*60)
    public void autoAssignClass() {
        LocalDate now = LocalDate.now();
        List<TrainingPlan> plans = trainingPlanRepository.findAll()
                .stream()
                .filter(e -> now.plusDays(7*2).isEqual(e.getStart()))
                .collect(Collectors.toList());
        for (TrainingPlan plan : plans) {
            assignClassForPlan(plan);
        }
    }

    private void assignClassForPlan(TrainingPlan plan) {
        List<TrainingOrder> orders = plan.getTrainingOrders();
        orders = orders.stream()
                .filter(e -> e.getStatus().equals(OrderStatus.PAID_UNASSIGNED))
                .sorted((o1, o2) -> {
                    LocalDateTime t1 = o1.getCreatedAt();
                    LocalDateTime t2 = o2.getCreatedAt();
                    if (t1.isBefore(t2)) return -1;
                    else if (t1.isAfter(t2)) return 1;
                    else return 0;
                })
                .collect(Collectors.toList());
        for (TrainingOrder order : orders) {
            assignClassForOrder(order);
        }

        repository.save(orders);
    }

    private void assignClassForOrder(TrainingOrder order) {

        TrainingPlan plan = order.getTrainingPlan();
        List<TrainingClass> classes = plan.getTrainingClasses();
        User user = order.getUser();
        List<Student> students = order.getStudents();
        int studentNum = students.size();

        int rest = 0;
        for (TrainingClass tc : classes) {
            rest += tc.getCapacity() - tc.getOccupancy();
        }

        if (rest < studentNum) {
            order.setStatus(OrderStatus.ASSIGN_FAILED);
            // refund
            double totalPrice = order.getTotalPrice();
            user.setBalance(user.getBalance() + totalPrice);
            user.setCredit(user.getCredit() - (int) totalPrice);
            plan.setProfit(plan.getProfit() - totalPrice);
        }
        else {
            int studentIndex = 0;
            for (TrainingClass tc : plan.getTrainingClasses()) {
                int classSize = tc.getCapacity() - tc.getOccupancy();
                for (int i = 0; i < classSize; i++) {
                    if (studentIndex >= studentNum)
                        break;
                    students.get(studentIndex).setTrainingClass(tc);
                    tc.setOccupancy(tc.getOccupancy()+1);
                    ++studentIndex;
                }
            }
            order.setStatus(OrderStatus.PAID_ASSIGNED);
        }

    }

}
