package cn.edu.nju.TrainingCollege.controller;

import cn.edu.nju.TrainingCollege.common.constant.Name;
import cn.edu.nju.TrainingCollege.common.constant.Notice;
import cn.edu.nju.TrainingCollege.common.exception.*;
import cn.edu.nju.TrainingCollege.common.util.CookieUtil;
import cn.edu.nju.TrainingCollege.common.util.ResponseResult;
import cn.edu.nju.TrainingCollege.configuration.listener.EmailActivationEvent;
import cn.edu.nju.TrainingCollege.domain.Profile;
import cn.edu.nju.TrainingCollege.domain.ProfileModifyForm;
import cn.edu.nju.TrainingCollege.domain.UserLoginForm;
import cn.edu.nju.TrainingCollege.domain.UserRegisterForm;
import cn.edu.nju.TrainingCollege.entity.User;
import cn.edu.nju.TrainingCollege.entity.VerificationToken;
import cn.edu.nju.TrainingCollege.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;

/**
 * @author hiki on 2018-01-24
 */

@RestController
@RequestMapping(value = "/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ApplicationEventPublisher eventPublisher;


    @PostMapping(value = "/register")
    public ResponseResult<User> register(WebRequest request,
                           UserRegisterForm form) {

        // create an account and send the activation email
        try {
            User user = userService.register(form);
            String appUrl = request.getContextPath();
            eventPublisher.publishEvent(new EmailActivationEvent(appUrl, user.getEmail()));
            return new ResponseResult<>(true, Notice.REGISTER_SUCCESS.toString(), user);
        } catch (EmailAlreadyExistsException ex) {
            return new ResponseResult<>(false, Notice.EMAIL_ALREADY_EXISTED.toString());
        }
    }

    /**
     * confirm registration page
     */
    @GetMapping(value = "/registrationConfirm")
    public String confirmRegistration(@RequestParam("token") String token) {
        VerificationToken verificationToken = userService.getVerificationToken(token);

        // Invalid.
        if (verificationToken == null) {
            return Notice.INVALID_TOKEN.toString();
        }

        // Expired.
        if (LocalDateTime.now().isAfter(verificationToken.getExpiryTime())) {
            return Notice.EXPIRED_TOKEN.toString();
        }

        // Successful to verify.
        User user = verificationToken.getUser();
        userService.activate(user.getId());
        return Notice.ACTIVATE_SUCCESS.toString();
    }


    @PostMapping(value = "/login")
    public ResponseResult<User> login(UserLoginForm form,
                                HttpServletResponse response,
                                HttpSession session) {
        try {
            User user = userService.login(form);
            response.addCookie(CookieUtil.of(Name.USER_LOGIN_NAME.toString(), user.getEmail()));
            session.setAttribute(Name.USER.toString(), user);
            return new ResponseResult<>(true, Notice.LOGIN_SUCCESS.toString(), user);
        } catch (UserNotExistsException e) {
            return new ResponseResult<>(false, Notice.USER_NOT_EXIST.toString());
        } catch (WrongPasswordException e) {
            return new ResponseResult<>(false, Notice.WRONG_PASSWORD.toString());
        } catch (UserNotActivatesException e) {
            return new ResponseResult<>(false, Notice.USER_INACTIVATED.toString());
        } catch (UserDisqualifiedException e) {
            return new ResponseResult<>(false, Notice.USER_DISQUALIFIED.toString());
        }

    }

    @GetMapping(value = "/checkLogin")
    public ResponseResult checkLogin(HttpSession session) {
        boolean isLogin = session.getAttribute(Name.USER.toString()) != null;
        return new ResponseResult(isLogin);
    }


    @GetMapping(value = "/logout")
    public ResponseResult logout(HttpServletRequest request,
                                 HttpServletResponse response,
                                 HttpSession session) {

        Cookie[] cookies = request.getCookies();
        for (Cookie cookie: cookies) {
            if (cookie.getName().equals(Name.USER_LOGIN_NAME.toString()))
                cookie.setMaxAge(0);
            response.addCookie(cookie);
        }
        session.removeAttribute(Name.USER.toString());
        return new ResponseResult(true);
    }


    @GetMapping(value = "/profile")
    public ResponseResult<Profile> profile(HttpSession session) {
        User user = (User) session.getAttribute(Name.USER.toString());
        Profile profile = userService.getProfile(user.getId());
        return new ResponseResult<>(true, Notice.QUERY_SUCCESS.toString(), profile);
    }


    @PostMapping(value = "/profile")
    public ResponseResult<User> modifyUserInfo(HttpSession session,
                                 ProfileModifyForm profileModifyForm) {
        User user = (User) session.getAttribute(Name.USER.toString());
        user = userService.modifyProfile(user.getId(), profileModifyForm);
        session.setAttribute(Name.USER.toString(), user);
        return new ResponseResult<>(true, Notice.QUERY_SUCCESS.toString(), user);
    }

    @GetMapping(value = "/useCredit")
    public ResponseResult<User> useCredit(HttpSession session) {
        User user = (User) session.getAttribute(Name.USER.toString());
        userService.useCredit(user.getId());
        User newUser = userService.findByEmail(user.getEmail());
        session.setAttribute(Name.USER.toString(), newUser);
        return new ResponseResult<>(true, Notice.INSTITUTE_MODIFY_SUCCESS.toString(), newUser);
    }

    @GetMapping(value = "/disqualify")
    public ResponseResult disqualify(HttpSession session,
                                     HttpServletRequest request,
                                     HttpServletResponse response) {
        User user = (User) session.getAttribute(Name.USER.toString());
        userService.disqualify(user.getId());

        Cookie[] cookies = request.getCookies();
        for (Cookie cookie: cookies) {
            if (cookie.getName().equals(Name.USER_LOGIN_NAME.toString()))
                cookie.setMaxAge(0);
            response.addCookie(cookie);
        }
        session.removeAttribute(Name.USER.toString());
        return new ResponseResult(true);
    }


}
