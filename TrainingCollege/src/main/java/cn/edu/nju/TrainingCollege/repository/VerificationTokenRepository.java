package cn.edu.nju.TrainingCollege.repository;

import cn.edu.nju.TrainingCollege.entity.User;
import cn.edu.nju.TrainingCollege.entity.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author hiki on 2018-01-26
 */

@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long>{

    VerificationToken findByToken(String token);

    VerificationToken findByUser(User user);
}
