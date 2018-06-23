package cn.edu.nju.TrainingCollege.controller;

import cn.edu.nju.TrainingCollege.common.constant.Name;
import cn.edu.nju.TrainingCollege.common.constant.Notice;
import cn.edu.nju.TrainingCollege.common.exception.UserNotExistsException;
import cn.edu.nju.TrainingCollege.common.exception.WrongPasswordException;
import cn.edu.nju.TrainingCollege.common.util.CookieUtil;
import cn.edu.nju.TrainingCollege.common.util.ResponseResult;
import cn.edu.nju.TrainingCollege.domain.ManagerLoginForm;
import cn.edu.nju.TrainingCollege.domain.Statistics;
import cn.edu.nju.TrainingCollege.entity.Manager;
import cn.edu.nju.TrainingCollege.service.InstituteService;
import cn.edu.nju.TrainingCollege.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author hiki on 2018-03-12
 */

@RestController
@RequestMapping(value = "/api/managers")
public class ManagerController {

    @Autowired
    private ManagerService managerService;

    @Autowired
    private InstituteService instituteService;

    @PostMapping(value = "/login")
    public ResponseResult<Manager> login(ManagerLoginForm managerLoginForm,
                        HttpServletResponse response,
                        HttpSession session) {
        try {
            Manager manager = managerService.login(managerLoginForm);
            response.addCookie(CookieUtil.of(Name.MANAGER_LOGIN_NAME.toString(), manager.getId().toString()));
            session.setAttribute(Name.MANAGER.toString(), manager);
            return new ResponseResult<>(true, Notice.LOGIN_SUCCESS.toString(), manager);
        } catch (UserNotExistsException ex) {
            return new ResponseResult<>(false, Notice.USER_NOT_EXIST.toString());
        } catch (WrongPasswordException ex) {
            return new ResponseResult<>(false, Notice.WRONG_PASSWORD.toString());
        }
    }

    @GetMapping(value = "/checkLogin")
    public ResponseResult checkLogin(HttpSession session) {
        boolean isLogin = session.getAttribute(Name.MANAGER.toString()) != null;
        return new ResponseResult(isLogin);
    }

    @GetMapping(value = "/logout")
    public ResponseResult logout(HttpServletRequest request,
                                 HttpServletResponse response,
                                 HttpSession session) {

        Cookie[] cookies = request.getCookies();
        for (Cookie cookie: cookies) {
            if (cookie.getName().equals(Name.MANAGER_LOGIN_NAME.toString()))
                cookie.setMaxAge(0);
            response.addCookie(cookie);
        }
        session.removeAttribute(Name.MANAGER.toString());

        return new ResponseResult(true);
    }

    @GetMapping(value = "/statistics")
    public ResponseResult<Statistics> statistics(HttpSession session) {
        Manager manager = (Manager) session.getAttribute(Name.MANAGER.toString());
        if (manager == null) {
            return new ResponseResult<>(false);
        }
        Statistics statistics = managerService.getStatistics(manager.getId());
        return new ResponseResult<>(true, Notice.QUERY_SUCCESS.toString(), statistics);
    }

}
