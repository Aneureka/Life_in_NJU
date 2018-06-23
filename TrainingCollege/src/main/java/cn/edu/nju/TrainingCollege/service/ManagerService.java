package cn.edu.nju.TrainingCollege.service;

import cn.edu.nju.TrainingCollege.common.exception.UserNotExistsException;
import cn.edu.nju.TrainingCollege.common.exception.WrongPasswordException;
import cn.edu.nju.TrainingCollege.domain.ManagerLoginForm;
import cn.edu.nju.TrainingCollege.domain.Statistics;
import cn.edu.nju.TrainingCollege.entity.Manager;

/**
 * @author hiki on 2018-03-12
 */

public interface ManagerService {

    Manager login(ManagerLoginForm managerLoginForm) throws UserNotExistsException, WrongPasswordException;

    Statistics getStatistics(Long id);
}
