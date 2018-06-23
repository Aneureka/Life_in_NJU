package cn.edu.nju.TrainingCollege.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author hiki on 2018-01-22
 */

@Controller
public class MainController {

    @GetMapping(value = "/ping")
    @ResponseBody
    public String ping() {
        return "Hello, link starts!";
    }

    @GetMapping(value = "/")
    public String index() {
        return "index.html";
    }

}
