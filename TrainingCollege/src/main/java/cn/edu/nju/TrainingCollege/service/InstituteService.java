package cn.edu.nju.TrainingCollege.service;

import cn.edu.nju.TrainingCollege.common.exception.EmailAlreadyExistsException;
import cn.edu.nju.TrainingCollege.common.exception.UserNotExistsException;
import cn.edu.nju.TrainingCollege.common.exception.WrongPasswordException;
import cn.edu.nju.TrainingCollege.domain.InstituteApplicationForm;
import cn.edu.nju.TrainingCollege.domain.InstituteLoginForm;
import cn.edu.nju.TrainingCollege.domain.InstituteProfileModifyForm;
import cn.edu.nju.TrainingCollege.domain.InstituteRegisterForm;
import cn.edu.nju.TrainingCollege.entity.Institute;
import cn.edu.nju.TrainingCollege.entity.InstituteApplicationLog;

import java.util.List;

/**
 * @author hiki on 2018-01-30
 */

public interface InstituteService {

    InstituteApplicationLog register(InstituteRegisterForm form) throws EmailAlreadyExistsException;

    Institute login(InstituteLoginForm form) throws UserNotExistsException, WrongPasswordException;

    InstituteApplicationLog modifyProfile(Long id, InstituteProfileModifyForm form);

    Institute find(Long id);

    InstituteApplicationLog handleApplication(InstituteApplicationForm form);

    List<InstituteApplicationLog> getApplicationLogs();

    InstituteApplicationLog getApplicationLog(Long id);

    Integer getInstituteNumber();

}
