package cn.edu.nju.TrainingCollege.service;

import cn.edu.nju.TrainingCollege.common.exception.PlanNotExpiredException;
import cn.edu.nju.TrainingCollege.domain.Lesson;
import cn.edu.nju.TrainingCollege.domain.LessonForInstitute;
import cn.edu.nju.TrainingCollege.domain.LessonForManager;
import cn.edu.nju.TrainingCollege.domain.TrainingPlanPublishForm;
import cn.edu.nju.TrainingCollege.entity.TrainingPlan;

import java.util.List;

/**
 * @author hiki on 2018-03-22
 */

public interface TrainingPlanService {

    TrainingPlan create(Long instituteId, TrainingPlanPublishForm form);

    TrainingPlan find(Long id);

    List<Lesson> getLessons();

    List<LessonForInstitute> getLessonsForInstitute(Long instituteId);

    List<LessonForManager> getLessonsForManager();

    List<String> getClasses(Long trainingPlanId);

    void remove(Long id);

    void settle(Long id) throws PlanNotExpiredException;

    Integer getLessonNumber();

}
