package cn.edu.nju.TrainingCollege.repository;

import cn.edu.nju.TrainingCollege.entity.InstituteApplicationLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author hiki on 2018-01-30
 */

@Repository
public interface InstituteApplicationLogRepository extends JpaRepository<InstituteApplicationLog, Long> {



}
