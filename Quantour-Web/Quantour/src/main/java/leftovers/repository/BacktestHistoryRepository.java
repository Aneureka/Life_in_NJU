package leftovers.repository;

import leftovers.model.Algorithm;
import leftovers.model.BacktestHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Hiki on 2017/6/12.
 */
@Repository
@Transactional
public interface BacktestHistoryRepository extends JpaRepository<BacktestHistory, Integer> {

    List<BacktestHistory> findByAlgoId(@Param("algoId") String algoId);

    BacktestHistory findDistinctFirstByAlgoIdOrderByTimeDesc(@Param("algoId") String algoId);

    long deleteByAlgoId(@Param("algoId") String algoId);


}
