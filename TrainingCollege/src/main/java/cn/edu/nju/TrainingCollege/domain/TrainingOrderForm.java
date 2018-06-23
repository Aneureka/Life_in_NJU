package cn.edu.nju.TrainingCollege.domain;

import java.util.List;

/**
 * @author hiki on 2018-04-05
 */

public class TrainingOrderForm {

    private Long lessonId;

    private String selectingClass;

    private List<String> studentNames;

    public TrainingOrderForm() {
    }

    public TrainingOrderForm(Long lessonId, String selectingClass, List<String> studentNames) {
        this.lessonId = lessonId;
        this.selectingClass = selectingClass;
        this.studentNames = studentNames;
    }

    public Long getLessonId() {
        return lessonId;
    }

    public void setLessonId(Long lessonId) {
        this.lessonId = lessonId;
    }

    public String getSelectingClass() {
        return selectingClass;
    }

    public void setSelectingClass(String selectingClass) {
        this.selectingClass = selectingClass;
    }

    public List<String> getStudentNames() {
        return studentNames;
    }

    public void setStudentNames(List<String> studentNames) {
        this.studentNames = studentNames;
    }
}
