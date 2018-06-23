package leftovers.repository;

import leftovers.model.Board;
import leftovers.model.StockCurInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

/**
 * Created by kevin on 2017/5/17.
 */
@Repository
@Transactional
public interface StockCurInfoRepository extends JpaRepository<StockCurInfo, String>{
    List<StockCurInfo> findAll();

    List<StockCurInfo> findByCodeIn(List<String> codes, Sort sort);

    Page<StockCurInfo> findByCodeIn(List<String> codes, Pageable pageable);
}
