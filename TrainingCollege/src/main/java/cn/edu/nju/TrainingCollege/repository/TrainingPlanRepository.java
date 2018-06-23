package cn.edu.nju.TrainingCollege.repository;

import cn.edu.nju.TrainingCollege.entity.Institute;
import cn.edu.nju.TrainingCollege.entity.TrainingPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author hiki on 2018-01-30
 */

@Repository
public interface TrainingPlanRepository extends JpaRepository<TrainingPlan, Long> {

    List<TrainingPlan> findByInstitute(Institute institute);

}
