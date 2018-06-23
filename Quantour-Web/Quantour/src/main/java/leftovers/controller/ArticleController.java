package leftovers.controller;

import leftovers.model.Algorithm;
import leftovers.model.Article;
import leftovers.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Hiki on 2017/6/14.
 */
@Controller
@RequestMapping(value = "/api", produces = "application/json;charset=UTF-8")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @RequestMapping(value = "/user/create", method = RequestMethod.POST)
    @ResponseBody
    public long createArticle(@RequestBody Article article){
        return articleService.createArticle(article);
    }

    @RequestMapping(value = "/findArticleById", method = RequestMethod.GET)
    public @ResponseBody
    Article findArticleById(@RequestParam int aid){
        return articleService.findArticleById(aid);
    }

    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    public @ResponseBody
    List<Article> findAll(){
        return articleService.findAll();
    }


}
