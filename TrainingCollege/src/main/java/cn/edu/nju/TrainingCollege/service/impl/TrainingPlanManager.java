package cn.edu.nju.TrainingCollege.service.impl;

import cn.edu.nju.TrainingCollege.common.exception.PlanNotExpiredException;
import cn.edu.nju.TrainingCollege.domain.*;
import cn.edu.nju.TrainingCollege.entity.Institute;
import cn.edu.nju.TrainingCollege.entity.Manager;
import cn.edu.nju.TrainingCollege.entity.TrainingClass;
import cn.edu.nju.TrainingCollege.entity.TrainingPlan;
import cn.edu.nju.TrainingCollege.repository.InstituteRepository;
import cn.edu.nju.TrainingCollege.repository.ManagerRepository;
import cn.edu.nju.TrainingCollege.repository.TrainingClassRepository;
import cn.edu.nju.TrainingCollege.repository.TrainingPlanRepository;
import cn.edu.nju.TrainingCollege.service.TrainingPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author hiki on 2018-03-22
 */

@Service
public class TrainingPlanManager implements TrainingPlanService {

    @Autowired
    private TrainingPlanRepository repository;

    @Autowired
    private TrainingClassRepository trainingClassRepository;

    @Autowired
    private InstituteRepository instituteRepository;

    @Autowired
    private ManagerRepository managerRepository;

    @Override
    public TrainingPlan create(Long instituteId, TrainingPlanPublishForm form) {
        Institute institute = instituteRepository.findOne(instituteId);
        TrainingPlan plan = TrainingPlan.fromInstituteAndPublishForm(institute, form);
        plan = repository.save(plan);
        List<TrainingClass> classes = new ArrayList<>();
        for (TrainingClassPublishForm f : form.getClasses()) {
            classes.add(TrainingClass.fromPlanAndPublishForm(plan, f));
        }
        trainingClassRepository.save(classes);
        return plan;
    }

    @Override
    public TrainingPlan find(Long id) {
        return null;
    }

    @Override
    public List<Lesson> getLessons() {
        return repository.findAll().stream()
                .filter(e -> !e.getStart().isBefore(LocalDate.now()))
                .map(Lesson::fromTrainingPlan)
                .collect(Collectors.toList());
    }

    @Override
    public List<LessonForInstitute> getLessonsForInstitute(Long instituteId) {
        List<TrainingPlan> plans = instituteRepository.findOne(instituteId).getTrainingPlans();
        return plans.stream().map(LessonForInstitute::fromTrainingPlan).collect(Collectors.toList());
    }

    @Override
    public List<LessonForManager> getLessonsForManager() {
        return repository.findAll()
                .stream()
//                .filter(e -> e.getEnd().isBefore(LocalDate.now()))
                .map(LessonForManager::fromTrainingPlan)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getClasses(Long trainingPlanId) {
        TrainingPlan plan = repository.findOne(trainingPlanId);
        return plan.getTrainingClasses().stream().map(TrainingClass::getTeacher).collect(Collectors.toList());
    }

    @Override
    public void remove(Long id) {
        repository.delete(id);
    }

    @Override
    public void settle(Long id) throws PlanNotExpiredException {
        TrainingPlan plan = repository.findOne(id);
        if (!plan.getEnd().isBefore(LocalDate.now())) {
            throw new PlanNotExpiredException();
        }

        Institute institute = plan.getInstitute();
        double totalProfit = plan.getProfit();
        double institutePart = 0.8 * totalProfit;
        double managerPart = totalProfit - institutePart;

        Manager manager = managerRepository.findAll().get(0);
        manager.setTotalProfit(manager.getTotalProfit() + managerPart);
        managerRepository.save(manager);

        institute.setProfit(institute.getProfit() + institutePart);
        plan.setProfit(0.0);
        repository.save(plan);
    }

    @Override
    public Integer getLessonNumber() {
        return repository.findAll().size();
    }


}
