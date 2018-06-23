package cn.edu.nju.TrainingCollege.controller;

import cn.edu.nju.TrainingCollege.common.constant.Name;
import cn.edu.nju.TrainingCollege.common.constant.Notice;
import cn.edu.nju.TrainingCollege.common.exception.BalanceInsufficientException;
import cn.edu.nju.TrainingCollege.common.exception.ClassAssignFailedException;
import cn.edu.nju.TrainingCollege.common.exception.PlanNotExpiredException;
import cn.edu.nju.TrainingCollege.common.util.ResponseResult;
import cn.edu.nju.TrainingCollege.domain.*;
import cn.edu.nju.TrainingCollege.entity.Institute;
import cn.edu.nju.TrainingCollege.entity.TrainingOrder;
import cn.edu.nju.TrainingCollege.entity.TrainingPlan;
import cn.edu.nju.TrainingCollege.entity.User;
import cn.edu.nju.TrainingCollege.service.StudentService;
import cn.edu.nju.TrainingCollege.service.TrainingOrderService;
import cn.edu.nju.TrainingCollege.service.TrainingPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author hiki on 2018-04-03
 */

@RestController
@RequestMapping(value = "/api/trainings")
public class TrainingController {

    private final TrainingPlanService service;

    private final TrainingOrderService trainingOrderService;

    private final StudentService studentService;

    @Autowired
    public TrainingController(TrainingPlanService service, TrainingOrderService trainingOrderService, StudentService studentService) {
        this.service = service;
        this.trainingOrderService = trainingOrderService;
        this.studentService = studentService;
    }

    @PostMapping(value = "", consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public ResponseResult create(HttpSession session,
                                  @RequestBody TrainingPlanPublishForm form) {
        Institute institute = (Institute) session.getAttribute(Name.INSTITUTE.toString());
        TrainingPlan plan = service.create(institute.getId(), form);
        session.setAttribute(Name.INSTITUTE.toString(), plan.getInstitute());
        return new ResponseResult(true);
    }

    @GetMapping(value = "")
    public ResponseResult<List<Lesson>> lessons() {
        List<Lesson> lessons = service.getLessons();
        return new ResponseResult<>(true, Notice.QUERY_SUCCESS.toString(), lessons);
    }

    @GetMapping(value = "/forInstitute")
    public ResponseResult<List<LessonForInstitute>> lessonsForInstitute(HttpSession session) {
        Institute institute = (Institute) session.getAttribute(Name.INSTITUTE.toString());
        if (institute == null) {
            return new ResponseResult<>(false);
        }
        List<LessonForInstitute> lessonsForInstitute = service.getLessonsForInstitute(institute.getId());
        return new ResponseResult<>(true, Notice.QUERY_SUCCESS.toString(), lessonsForInstitute);
    }

    @GetMapping(value = "/forManager")
    public ResponseResult<List<LessonForManager>> lessonsForManager() {
        List<LessonForManager> lessonsForManager = service.getLessonsForManager();
        return new ResponseResult<>(true, Notice.QUERY_SUCCESS.toString(), lessonsForManager);
    }

    @GetMapping(value = "/settle")
    public ResponseResult settle(@RequestParam("lessonId") Long lessonId) {
        try {
            service.settle(lessonId);
            return new ResponseResult<>(true);
        } catch (PlanNotExpiredException e) {
            return new ResponseResult<>(false, Notice.PLAN_NOT_EXPIRED.toString());
        }
    }

    @GetMapping(value = "/classes")
    public ResponseResult<List<String>> classes(@RequestParam("lessonId") Long lessonId) {
        List<String> classes = service.getClasses(lessonId);
        return new ResponseResult<>(true, Notice.QUERY_SUCCESS.toString(), classes);
    }

    @PostMapping(value = "/orders", consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public ResponseResult<TrainingOrder> order(HttpSession session,
                                @RequestBody TrainingOrderForm form) {
        User user = (User) session.getAttribute(Name.USER.toString());
        try {
            TrainingOrder order = trainingOrderService.create(user, form);
            session.setAttribute(Name.USER.toString(), order.getUser());
            return new ResponseResult<>(true, Notice.ORDER_SUCCESS.toString(), order);
        } catch (ClassAssignFailedException e) {
            return new ResponseResult<>(false, Notice.CLASS_ASSIGN_FAILED.toString());
        }
    }

    @GetMapping(value = "/orders")
    public ResponseResult<List<TrainingOrderInfo>> getOrders(HttpSession session) {
        User user = (User) session.getAttribute(Name.USER.toString());
        if (user == null) {
            return new ResponseResult<>(false);
        }
        List<TrainingOrderInfo> orders = trainingOrderService.findByUser(user.getId());
        return new ResponseResult<>(true, Notice.QUERY_SUCCESS.toString(), orders);
    }

    @GetMapping(value = "/orders/detail")
    public ResponseResult<OrderDetailInfo> getOrderDetailInfo(@RequestParam("orderId") Long orderId) {
        OrderDetailInfo order = trainingOrderService.getOrderDetailInfo(orderId);
        return new ResponseResult<>(true, Notice.QUERY_SUCCESS.toString(), order);
    }

    @GetMapping(value = "/orders/forLesson")
    public ResponseResult<List<OrderForLesson>> getOrdersForLesson(@RequestParam("lessonId") Long lessonId) {
        List<OrderForLesson> orderForLessons = trainingOrderService.getOrdersForLesson(lessonId);
        return new ResponseResult<>(true, Notice.QUERY_SUCCESS.toString(), orderForLessons);
    }

    @GetMapping(value = "/orders/withdraw")
    public ResponseResult withdraw(HttpSession session, Long orderId) {
        TrainingOrder order = trainingOrderService.findOne(orderId);
        order = trainingOrderService.withdraw(order);
        session.setAttribute(Name.USER.toString(), order.getUser());
        return new ResponseResult<>(true);
    }

    @GetMapping(value = "/orders/pay")
    public ResponseResult pay(HttpSession session, Long orderId) {
        TrainingOrder order = trainingOrderService.findOne(orderId);
        try {
            order = trainingOrderService.pay(order);
            session.setAttribute(Name.USER.toString(), order.getUser());
            return new ResponseResult<>(true);
        } catch (BalanceInsufficientException e) {
            return new ResponseResult(false, Notice.BALANCE_INSUFFICIENT.toString());
        }
    }

    @GetMapping(value = "/students/forLesson")
    public ResponseResult<List<StudentForLesson>> getStudentsForLesson(@RequestParam("lessonId") Long lessonId) {
        List<StudentForLesson> studentForLessons = studentService.getStudentsForLesson(lessonId);
        return new ResponseResult<>(true, Notice.QUERY_SUCCESS.toString(), studentForLessons);
    }

    @PostMapping(value = "/students/modify")
    public ResponseResult modifyStudentInfo(StudentModifyForm form) {
        studentService.modify(form);
        return new ResponseResult(true);
    }



}
