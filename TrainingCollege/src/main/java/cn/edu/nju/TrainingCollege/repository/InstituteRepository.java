package cn.edu.nju.TrainingCollege.repository;

import cn.edu.nju.TrainingCollege.entity.Institute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author hiki on 2018-01-28
 */

@Repository
public interface InstituteRepository extends JpaRepository<Institute, Long> {

    Institute findByEmail(String email);

}
