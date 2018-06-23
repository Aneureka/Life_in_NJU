package cn.edu.nju.TrainingCollege.repository;

import cn.edu.nju.TrainingCollege.common.constant.UserStatus;
import cn.edu.nju.TrainingCollege.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author hiki on 2018-01-24
 */

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

    User findByEmailAndPassword(String email, String password);

    List<User> findByName(String name);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update User u set u.status = :status where u.id = :userId")
    int updateEnabled(@Param("userId") Long id, @Param("status")UserStatus status);
}
