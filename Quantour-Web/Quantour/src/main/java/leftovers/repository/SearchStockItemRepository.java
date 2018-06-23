package leftovers.repository;

import leftovers.model.SearchStockItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by kevin on 2017/6/7.
 */
@Repository
@Transactional
public interface SearchStockItemRepository extends JpaRepository<SearchStockItem, String>{
}
