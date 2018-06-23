package cn.edu.nju.TrainingCollege.controller;

import cn.edu.nju.TrainingCollege.common.constant.Name;
import cn.edu.nju.TrainingCollege.common.constant.Notice;
import cn.edu.nju.TrainingCollege.common.exception.EmailAlreadyExistsException;
import cn.edu.nju.TrainingCollege.common.exception.UserNotExistsException;
import cn.edu.nju.TrainingCollege.common.exception.WrongPasswordException;
import cn.edu.nju.TrainingCollege.common.util.CookieUtil;
import cn.edu.nju.TrainingCollege.common.util.ResponseResult;
import cn.edu.nju.TrainingCollege.domain.InstituteApplicationForm;
import cn.edu.nju.TrainingCollege.domain.InstituteLoginForm;
import cn.edu.nju.TrainingCollege.domain.InstituteProfileModifyForm;
import cn.edu.nju.TrainingCollege.domain.InstituteRegisterForm;
import cn.edu.nju.TrainingCollege.entity.Institute;
import cn.edu.nju.TrainingCollege.entity.InstituteApplicationLog;
import cn.edu.nju.TrainingCollege.service.InstituteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author hiki on 2018-03-12
 */

@RestController
@RequestMapping(value = "/api/institutes")
public class InstituteController {

    @Autowired
    private InstituteService instituteService;

    @PostMapping(value = "/register")
    public ResponseResult register(InstituteRegisterForm instituteRegisterForm) {
        try {
            instituteService.register(instituteRegisterForm);
            return new ResponseResult<>(true, Notice.INSTITUTE_REGISTER_SUCCESS.toString());
        } catch (EmailAlreadyExistsException ex) {
            return new ResponseResult<>(false, Notice.EMAIL_ALREADY_EXISTED.toString());
        }
    }


    @PostMapping(value = "/login")
    public ResponseResult<Institute> login(HttpServletResponse response,
                                InstituteLoginForm instituteLoginForm,
                                HttpSession session) {
        try {
            Institute institute = instituteService.login(instituteLoginForm);
            response.addCookie(CookieUtil.of(Name.INSTITUTE_LOGIN_NAME.toString(), institute.getId().toString()));
            session.setAttribute(Name.INSTITUTE.toString(), institute);
            return new ResponseResult<>(true, Notice.LOGIN_SUCCESS.toString(), institute);
        } catch (UserNotExistsException ex) {
            return new ResponseResult<>(false, Notice.USER_NOT_EXIST.toString());
        } catch (WrongPasswordException ex) {
            return new ResponseResult<>(false, Notice.WRONG_PASSWORD.toString());
        }

    }

    @GetMapping(value = "/checkLogin")
    public ResponseResult checkLogin(HttpSession session) {
        boolean isLogin = session.getAttribute(Name.INSTITUTE.toString()) != null;
        return new ResponseResult(isLogin);
    }

    @GetMapping(value = "/logout")
    public ResponseResult logout(HttpServletRequest request,
                                 HttpServletResponse response,
                                 HttpSession session) {

        Cookie[] cookies = request.getCookies();
        for (Cookie cookie: cookies) {
            if (cookie.getName().equals(Name.INSTITUTE_LOGIN_NAME.toString()))
                cookie.setMaxAge(0);
            response.addCookie(cookie);
        }
        session.removeAttribute(Name.INSTITUTE.toString());

        return new ResponseResult(true);
    }

    @GetMapping(value = "/applicationLogs")
    public ResponseResult<List<InstituteApplicationLog>> getApplicationLogs() {
        List<InstituteApplicationLog> logs = instituteService.getApplicationLogs();
        if (logs == null || logs.isEmpty()) {
            return new ResponseResult<>(false, Notice.EMPTY_LIST.toString());
        }
        return new ResponseResult<>(true, Notice.QUERY_SUCCESS.toString(), logs);
    }

    @GetMapping(value = "/applicationLog")
    public ResponseResult<InstituteApplicationLog> getApplicationLog(@RequestParam("id") Long id) {
        InstituteApplicationLog log = instituteService.getApplicationLog(id);
        return new ResponseResult<>(true, Notice.QUERY_SUCCESS.toString(), log);
    }

    @PostMapping(value = "/applicationForm")
    public ResponseResult<InstituteApplicationLog> getApplicationLog(InstituteApplicationForm form) {
        InstituteApplicationLog log = instituteService.handleApplication(form);
        return new ResponseResult<>(true, Notice.QUERY_SUCCESS.toString(), log);
    }

    @GetMapping(value = "/profile")
    public ResponseResult<Institute> profile(HttpSession session) {
        Institute institute = (Institute) session.getAttribute(Name.INSTITUTE.toString());
        return new ResponseResult<>(true, Notice.QUERY_SUCCESS.toString(), institute);
    }

    @PostMapping(value = "/profile")
    public ResponseResult profile(HttpSession session,
                                             InstituteProfileModifyForm form) {
        Institute institute = (Institute) session.getAttribute(Name.INSTITUTE.toString());
        instituteService.modifyProfile(institute.getId(), form);
        return new ResponseResult<>(true, Notice.INSTITUTE_MODIFY_SUCCESS.toString());
    }

}
