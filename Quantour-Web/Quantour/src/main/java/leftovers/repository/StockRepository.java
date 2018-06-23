package leftovers.repository;

import leftovers.model.StockDData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by kevin on 2017/5/12.
 */
@Repository
@Transactional
public interface StockRepository extends JpaRepository<StockDData, StockDData.StockDDataPK> {
    List<StockDData> findByPkCode(String code);

    List<StockDData> findByPkCodeAndPkDateAfter(String code, LocalDate date);

    @Query("SELECT sdd.close FROM StockDData sdd where sdd.pk.code = :code and sdd.pk.date > :date")
    List<Double> findCloseByPkCodeAndPkDateAfter(@Param("code") String code, @Param("date") LocalDate date);

}
