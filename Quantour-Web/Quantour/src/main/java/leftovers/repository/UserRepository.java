package leftovers.repository;

import leftovers.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by kevin on 2017/5/16.
 */
@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, String>{
    User findByUsername(String username);

    List<User> findByEmailAddress(String emailAddress);

    List<User> findByPhoneNumber(String phoneNumber);

    @Modifying
    @Query(value = "UPDATE User user set user.password = :password where user.username = :username")
    void updatePassword(@Param(value = "username") String username, @Param(value = "password") String password);
}