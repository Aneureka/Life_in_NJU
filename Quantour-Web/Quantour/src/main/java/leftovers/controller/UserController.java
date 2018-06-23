package leftovers.controller;

import com.alibaba.fastjson.JSONObject;
import javassist.NotFoundException;
import leftovers.controller.support.ChangePasswordForm;
import leftovers.controller.support.UserInfoProvider;
import leftovers.model.User;
import leftovers.model.WatchlistItem;
import leftovers.service.UserService;
import leftovers.service.exceptions.BadFieldValueException;
import leftovers.util.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.management.BadAttributeValueExpException;
import java.util.List;

/**
 * Created by kevin on 2017/5/15.
 */
@Controller
@RequestMapping(value = "/api/user", produces = "application/json;charset=UTF-8")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserInfoProvider userInfoProvider;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(){
        return "index";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public @ResponseBody String createUser(@RequestBody User user){
        User result = null;
        try {
            result = userService.createUser(user);
            return JSONResult.fillResultString(200, "User has been created successfully.", JSONObject.parse(result.toJSON()));
        } catch (BadFieldValueException e) {
            return JSONResult.fillResultString(e.getStatus(), e.getMessage(), org.json.JSONObject.NULL);
        }
    }

    @RequestMapping(value = "/checkRegisterInfo", method = RequestMethod.POST)
    public @ResponseBody String checkRegisterInfo(@RequestBody User user){
        try {
            userService.checkRegisterInfoForTest(user);
            return JSONResult.fillResultString(200, "User info correct.", org.json.JSONObject.NULL);
        } catch (BadFieldValueException e) {
            return JSONResult.fillResultString(e.getStatus(), e.getMessage(), org.json.JSONObject.NULL);
        }
    }

    @RequestMapping(value = "/getUserInfo", method = RequestMethod.GET)
    public @ResponseBody String getUserInfo(){
        return userService.findByUsername(userInfoProvider.getCurrentUser()).toJSON();
    }

    @RequestMapping(value = "/changePassword", method = RequestMethod.POST)
    public @ResponseBody String changePassword(@RequestBody ChangePasswordForm changePasswordForm){
        try {
            userService.changePassword(changePasswordForm);
        } catch (javax.ws.rs.NotFoundException e) {
            e.printStackTrace();
            return JSONResult.fillResultString(500, e.getMessage(), null);
        }
        return JSONResult.fillResultString(200, "密码更新成功", "success");
    }

    @RequestMapping(value = "/forgetPassword", method = RequestMethod.POST)
    public @ResponseBody String forgetPassword(@RequestBody ChangePasswordForm changePasswordForm){
        try {
            userService.forgetPassword(changePasswordForm);
        } catch (javax.ws.rs.NotFoundException e) {
            e.printStackTrace();
            return JSONResult.fillResultString(500, e.getMessage(), null);
        }
        return JSONResult.fillResultString(200, "密码更新成功", "success");
    }

    @RequestMapping(value = "watchlist/getlist", method = RequestMethod.GET)
    public @ResponseBody List<WatchlistItem> getWatchlist(){
        return userService.getWatchlist(userInfoProvider.getCurrentUser());
    }

    @RequestMapping(value = "watchlist/additem")
    public @ResponseBody WatchlistItem addWatchlistItem(@RequestParam String code){
        return userService.addWatchlistItem(userInfoProvider.getCurrentUser(), code);
    }

    @RequestMapping(value = "watchlist/removeitem")
    public @ResponseBody List<WatchlistItem> removeWatchlistItem(@RequestParam String code){
        return userService.removeWatchlistItem(userInfoProvider.getCurrentUser(), code);
    }
}
