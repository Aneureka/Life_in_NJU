package leftovers.repository;

import leftovers.model.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by kevin on 2017/6/5.
 */
@Repository
@Transactional
public interface BoardRepository extends JpaRepository<Board, Integer> {
    @Query("SELECT b.code FROM Board b where b.board = :board")
    List<String> findCodeByBoard(@Param("board") String board);
}
