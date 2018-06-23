package leftovers.repository;

import leftovers.model.Algorithm;
import leftovers.model.Article;
import leftovers.model.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Hiki on 2017/6/14.
 */
@Repository
@Transactional
public interface ArticleRepository extends JpaRepository<Article, Integer> {

    List<Article> findByUsername(@Param("username") String username);

    long deleteByAid(@Param("aid") int aid);


}
