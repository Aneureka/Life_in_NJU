package cn.edu.nju.TrainingCollege.service;

import cn.edu.nju.TrainingCollege.common.exception.*;
import cn.edu.nju.TrainingCollege.domain.Profile;
import cn.edu.nju.TrainingCollege.domain.ProfileModifyForm;
import cn.edu.nju.TrainingCollege.domain.UserLoginForm;
import cn.edu.nju.TrainingCollege.domain.UserRegisterForm;
import cn.edu.nju.TrainingCollege.entity.User;
import cn.edu.nju.TrainingCollege.entity.VerificationToken;

/**
 * @author hiki on 2018-01-24
 */

public interface UserService {

    User register(UserRegisterForm userRegisterForm) throws EmailAlreadyExistsException;

    User login(UserLoginForm userLoginForm) throws UserNotExistsException, WrongPasswordException, UserNotActivatesException, UserDisqualifiedException;

    void activate(Long id);

    void disqualify(Long id);

    Profile getProfile(Long id);

    User modifyProfile(Long id, ProfileModifyForm form);

    void useCredit(Long id);

    User findByEmail(String email);

    User findByToken(String verificationToken);

    boolean isExisted(String email);

    void createVerificationToken(User user, String token);

    VerificationToken getVerificationToken(String verificationToken);

    Integer getUserNumber();

}
