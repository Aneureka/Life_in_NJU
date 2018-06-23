package cn.edu.nju.TrainingCollege.service;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author hiki on 2018-04-03
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class TrainingPlanServiceTest {

    @Autowired
    private TrainingPlanService tested;

    @Autowired
    private InstituteService instituteService;

//    @Test
//    public void create() {
//        System.out.println(LocalDate.now().toString());
//        Institute institute = instituteService.find(1000002L);
//        List<TrainingClassPublishForm> classes = new ArrayList<>();
//        classes.add(new TrainingClassPublishForm("王老师", 20));
//        classes.add(new TrainingClassPublishForm("张老师", 10));
//        TrainingPlanPublishForm form = new TrainingPlanPublishForm(
//                "夏季学车",
//                "包学包会",
//                3000.0,
//                LocalDate.now().toString(),
//                LocalDate.now().toString(),
//                "两天一次",
//                classes
//        );
//        tested.create(institute.getId(), form);
//    }
}