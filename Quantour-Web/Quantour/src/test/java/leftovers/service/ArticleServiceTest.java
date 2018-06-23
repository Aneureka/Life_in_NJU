package leftovers.service;

import leftovers.model.Article;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

/**
 * Created by Hiki on 2017/6/14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class ArticleServiceTest {

    @Autowired
    ArticleService tested;

    String title = "哈哈";
    String content = "呵呵呵呵呵呵呵";
    String username = "Hiki";
    String time = LocalDateTime.now().toString();
    int readNum = 0;

    Article article = new Article(title, content, username, time, readNum);

    @Test
    public void createArticle() throws Exception {
        tested.createArticle(article);
    }

    @Test
    public void updateArticle() throws Exception {
    }

    @Test
    public void findArticleById() throws Exception {
        Article article = tested.findArticleById(405);
        System.out.println(article.getReadNum());
    }

    @Test
    public void findAll() throws Exception {
        tested.findAll().forEach(e -> System.out.println(e.getTitle()));
    }

    @Test
    public void removeArticle() throws Exception {
    }

    @Test
    public void findArticlesByUsername() throws Exception {
    }

}