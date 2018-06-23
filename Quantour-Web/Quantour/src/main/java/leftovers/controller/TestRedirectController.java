package leftovers.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by kevin on 2017/6/9.
 */
@Controller
@RequestMapping("/redirect")
public class TestRedirectController {
    @RequestMapping("toHome")
    public String directRedirect(){
        return "redirect:/";
    }
}
