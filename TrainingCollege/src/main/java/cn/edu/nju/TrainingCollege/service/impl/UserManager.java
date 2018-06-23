package cn.edu.nju.TrainingCollege.service.impl;

import cn.edu.nju.TrainingCollege.common.constant.UserStatus;
import cn.edu.nju.TrainingCollege.common.exception.*;
import cn.edu.nju.TrainingCollege.domain.Profile;
import cn.edu.nju.TrainingCollege.domain.ProfileModifyForm;
import cn.edu.nju.TrainingCollege.domain.UserLoginForm;
import cn.edu.nju.TrainingCollege.domain.UserRegisterForm;
import cn.edu.nju.TrainingCollege.entity.User;
import cn.edu.nju.TrainingCollege.entity.VerificationToken;
import cn.edu.nju.TrainingCollege.repository.UserRepository;
import cn.edu.nju.TrainingCollege.repository.VerificationTokenRepository;
import cn.edu.nju.TrainingCollege.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * @author hiki on 2018-01-24
 */

@Service
public class UserManager implements UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private VerificationTokenRepository tokenRepository;

    @Override
    @Transactional
    public User register(UserRegisterForm userRegisterForm) throws EmailAlreadyExistsException {
        if (isExisted(userRegisterForm.getEmail())) {
            throw new EmailAlreadyExistsException(userRegisterForm.getEmail());
        }
        return repository.save(User.fromUserRegisterForm(userRegisterForm));
    }

    @Override
    public User login(UserLoginForm userLoginForm) throws UserNotExistsException, WrongPasswordException, UserNotActivatesException, UserDisqualifiedException {

        // retrieve data
        String email = userLoginForm.getEmail();
        String password = userLoginForm.getPassword();

        User user = repository.findByEmail(email);
        if (user == null)
            throw new UserNotExistsException(email);
        if (password == null || !password.equals(user.getPassword()))
            throw new WrongPasswordException(email);
        if (UserStatus.INACTIVATED.equals(user.getStatus()))
            throw new UserNotActivatesException(email);
        if (UserStatus.DISQUALIFIED.equals(user.getStatus()))
            throw new UserDisqualifiedException(email);

        return user;
    }

    @Override
    public void activate(Long id) {
        repository.updateEnabled(id, UserStatus.ACTIVATED);
    }

    @Override
    public void disqualify(Long id) {
        repository.updateEnabled(id, UserStatus.DISQUALIFIED);
    }

    @Override
    public Profile getProfile(Long id) {
        return toUserInfo(repository.findOne(id));
    }

    @Override
    public User modifyProfile(Long id, ProfileModifyForm form) {
        User user = repository.findOne(id);
        user.setName(form.getName());
        return repository.save(user);
    }

    @Override
    public void useCredit(Long id) {
        User user = repository.findOne(id);
        int credit = user.getCredit();
        int bonus = credit / 100;
        user.setCredit(credit - bonus*100);
        user.setBalance(user.getBalance() + bonus);
        repository.save(user);
    }

    @Override
    public User findByEmail(String email) {
        return repository.findByEmail(email);
    }

    @Override
    public User findByToken(String verificationToken) {
        return tokenRepository.findByToken(verificationToken).getUser();
    }

    @Override
    public boolean isExisted(String email) {
        return findByEmail(email) != null;
    }

    @Override
    public void createVerificationToken(User user, String token) {
        VerificationToken newToken = new VerificationToken(token, user);
        tokenRepository.save(newToken);
    }

    @Override
    public VerificationToken getVerificationToken(String verificationToken) {
        return tokenRepository.findByToken(verificationToken);
    }

    @Override
    public Integer getUserNumber() {
        return repository.findAll().size();
    }


    private Profile toUserInfo(User user) {
        return new Profile(
                user.getId(),
                user.getName(),
                user.getCreatedAt(),
                user.getBalance(),
                user.getRate(),
                user.getCredit()
        );
    }
}
