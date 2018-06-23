package leftovers.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import leftovers.controller.support.UserInfoProvider;
import leftovers.service.CommentService;
import leftovers.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;

/**
 * Created by kevin on 2017/5/24.
 */
@Controller
@RequestMapping(value = "/api/comment", produces = "application/json;charset=UTF-8")
public class CommentController {
    @Autowired
    CommentService commentService;

    @Autowired
    UserInfoProvider userInfoProvider;

    @Autowired
    UserService userService;

    @RequestMapping("/comment")
    public String getComment(){
        return "comment";
    }

    @RequestMapping("/get_remote_auth_s3")
    @ResponseBody
    public String getRemoteAuthS3(){
        try {
            String remote_auth_s3 = commentService.getRemoteAuthS3(userService.findByUsername(userInfoProvider.getCurrentUser()));
            return "{\"remote_auth_s3\":\"" + remote_auth_s3 + "\"}";
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (SignatureException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }
}
