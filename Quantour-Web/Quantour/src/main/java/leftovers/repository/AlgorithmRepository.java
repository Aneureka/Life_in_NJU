package leftovers.repository;

import leftovers.model.Algorithm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Hiki on 2017/6/9.
 */

@Repository
@Transactional
public interface AlgorithmRepository extends JpaRepository<Algorithm, String> {

    List<Algorithm> findByUsername(@Param("username") String username);

    long deleteByAlgoId(@Param("algoId") String algoId);


}
