package leftovers.repository;

import leftovers.model.WatchlistItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by kevin on 2017/6/7.
 */
@Repository
@Transactional
public interface WatchListRepository extends JpaRepository<WatchlistItem, Integer>{
    List<WatchlistItem> findByUsername(String username);

    Page<WatchlistItem> findByUsername(String username, Pageable pageable);

    WatchlistItem findByUsernameAndCode(String username, String code);

    List<WatchlistItem> deleteByUsernameAndCode(String username, String code);
}
