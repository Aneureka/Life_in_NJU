package cn.edu.nju.TrainingCollege.service;

import cn.edu.nju.TrainingCollege.domain.StudentForLesson;
import cn.edu.nju.TrainingCollege.domain.StudentModifyForm;
import cn.edu.nju.TrainingCollege.entity.Student;

import java.util.List;

/**
 * @author hiki on 2018-04-06
 */

public interface StudentService {

    Student modify(StudentModifyForm form);

    List<StudentForLesson> getStudentsForLesson(Long id);

    Integer getStudentNumber();
}
