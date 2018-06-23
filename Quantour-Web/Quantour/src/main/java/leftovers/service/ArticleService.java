package leftovers.service;

import leftovers.model.Article;
import leftovers.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Created by Hiki on 2017/6/14.
 */
@Service
public class ArticleService {

    @Autowired
    ArticleRepository articleRepository;

    public long createArticle(Article article) {
        if (articleRepository.findOne(article.getAid()) != null)
            return 0;
        article.setReadNum(0);
        articleRepository.saveAndFlush(article);
        return 1;
    }

    public long updateArticle(Article article){
        if (articleRepository.findOne(article.getAid()) == null)
            return 0;
        articleRepository.saveAndFlush(article);
        return 1;
    }

    public Article findArticleById(int aid){
        Article article = articleRepository.findOne(aid);
        article.setReadNum(article.getReadNum() + 1);
        updateArticle(article);
        return article;
    }

    public List<Article> findAll(){
        List<Article> articles = articleRepository.findAll();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        articles.sort((o1, o2) -> LocalDateTime.parse(o2.getTime(), formatter).compareTo(LocalDateTime.parse(o1.getTime(), formatter)));
        return articles;
    }

    public long removeArticle(int aid){
        return articleRepository.deleteByAid(aid);
    }

    public List<Article> findArticlesByUsername(String username){
        return articleRepository.findByUsername(username);
    }


}
