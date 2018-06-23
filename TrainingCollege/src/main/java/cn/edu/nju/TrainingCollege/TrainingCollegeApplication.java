package cn.edu.nju.TrainingCollege;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TrainingCollegeApplication {

    public static void main(String[] args) {
        SpringApplication.run(TrainingCollegeApplication.class, args);
    }

}
