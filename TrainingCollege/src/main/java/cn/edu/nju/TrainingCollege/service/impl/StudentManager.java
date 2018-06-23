package cn.edu.nju.TrainingCollege.service.impl;

import cn.edu.nju.TrainingCollege.domain.StudentForLesson;
import cn.edu.nju.TrainingCollege.domain.StudentModifyForm;
import cn.edu.nju.TrainingCollege.entity.Student;
import cn.edu.nju.TrainingCollege.entity.TrainingClass;
import cn.edu.nju.TrainingCollege.entity.TrainingPlan;
import cn.edu.nju.TrainingCollege.repository.StudentRepository;
import cn.edu.nju.TrainingCollege.repository.TrainingPlanRepository;
import cn.edu.nju.TrainingCollege.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author hiki on 2018-04-06
 */

@Service
public class StudentManager implements StudentService {

    @Autowired
    private StudentRepository repository;

    @Autowired
    private TrainingPlanRepository trainingPlanRepository;

    @Override
    public Student modify(StudentModifyForm form) {
        Student student = repository.findOne(form.getStudentId());

        if (form.getBeganAt() != null && !form.getBeganAt().equals("")) {
            student.setBeganAt(LocalDate.parse(form.getBeganAt()));
        }

        Double score = form.getScore();
        if (score != null) {
            student.setScore(score);
        }
        return repository.save(student);
    }

    @Override
    public List<StudentForLesson> getStudentsForLesson(Long lessonId) {
        TrainingPlan plan = trainingPlanRepository.findOne(lessonId);
        List<Student> students = new ArrayList<>();
        for (TrainingClass tc : plan.getTrainingClasses()) {
            students.addAll(tc.getStudents());
        }
        return students.stream().map(StudentForLesson::fromStudent).collect(Collectors.toList());
    }

    @Override
    public Integer getStudentNumber() {
        return repository.findAll().size();
    }
}
