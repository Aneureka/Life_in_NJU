package cn.edu.nju.TrainingCollege.service.impl;

import cn.edu.nju.TrainingCollege.common.exception.UserNotExistsException;
import cn.edu.nju.TrainingCollege.common.exception.WrongPasswordException;
import cn.edu.nju.TrainingCollege.domain.ManagerLoginForm;
import cn.edu.nju.TrainingCollege.domain.Statistics;
import cn.edu.nju.TrainingCollege.entity.Manager;
import cn.edu.nju.TrainingCollege.repository.*;
import cn.edu.nju.TrainingCollege.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author hiki on 2018-03-12
 */
@Service
public class ManagerManager implements ManagerService {

    private final ManagerRepository repository;

    private final UserRepository userRepository;

    private final InstituteRepository instituteRepository;

    private final StudentRepository studentRepository;

    private final TrainingPlanRepository trainingPlanRepository;

    private final TrainingOrderRepository trainingOrderRepository;

    @Autowired
    public ManagerManager(ManagerRepository repository, UserRepository userRepository, InstituteRepository instituteRepository, StudentRepository studentRepository, TrainingPlanRepository trainingPlanRepository, TrainingOrderRepository trainingOrderRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.instituteRepository = instituteRepository;
        this.studentRepository = studentRepository;
        this.trainingPlanRepository = trainingPlanRepository;
        this.trainingOrderRepository = trainingOrderRepository;
    }

    @Override
    public Manager login(ManagerLoginForm managerLoginForm) throws UserNotExistsException, WrongPasswordException {

        Long managerId = managerLoginForm.getId();
        String password = managerLoginForm.getPassword();

        Manager manager = repository.findOne(managerId);

        if (manager == null)
            throw new UserNotExistsException("不存在该管理员");
        if (password == null || !password.equals(manager.getPassword()))
            throw new WrongPasswordException();

        return manager;

    }

    @Override
    public Statistics getStatistics(Long id) {
        double totalProfit = repository.findOne(id).getTotalProfit();
        int userNumber = userRepository.findAll().size();
        int instituteNumber = instituteRepository.findAll().size();
        int lessonNumber = trainingPlanRepository.findAll().size();
        int orderNumber = trainingOrderRepository.findAll().size();
        int studentNumber = studentRepository.findAll().size();
        return new Statistics(
                totalProfit,
                userNumber,
                instituteNumber,
                lessonNumber,
                orderNumber,
                studentNumber
        );
    }


}
