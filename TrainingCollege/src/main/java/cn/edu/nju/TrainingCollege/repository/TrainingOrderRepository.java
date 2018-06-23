package cn.edu.nju.TrainingCollege.repository;

import cn.edu.nju.TrainingCollege.entity.TrainingOrder;
import cn.edu.nju.TrainingCollege.entity.TrainingPlan;
import cn.edu.nju.TrainingCollege.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author hiki on 2018-01-30
 */

@Repository
public interface TrainingOrderRepository extends JpaRepository<TrainingOrder, Long> {

    List<TrainingOrder> findByTrainingPlan(TrainingPlan plan);

    List<TrainingOrder> findByUser(User user);

}
