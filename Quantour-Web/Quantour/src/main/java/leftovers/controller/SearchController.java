package leftovers.controller;

import leftovers.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by kevin on 2017/6/8.
 */
@Controller
@RequestMapping(value = "/api/search", produces = "application/json;charset=UTF-8")
public class SearchController {
    @Autowired
    private SearchService searchService;

    @RequestMapping(name = "")
    @ResponseBody
    public String search(@RequestParam String input){
        return searchService.search(input);
    }
}
