package cn.edu.nju.TrainingCollege.service.impl;

import cn.edu.nju.TrainingCollege.common.EmailHandler;
import cn.edu.nju.TrainingCollege.common.constant.InstituteApplicationStatus;
import cn.edu.nju.TrainingCollege.common.exception.EmailAlreadyExistsException;
import cn.edu.nju.TrainingCollege.common.exception.UserNotExistsException;
import cn.edu.nju.TrainingCollege.common.exception.WrongPasswordException;
import cn.edu.nju.TrainingCollege.domain.InstituteApplicationForm;
import cn.edu.nju.TrainingCollege.domain.InstituteLoginForm;
import cn.edu.nju.TrainingCollege.domain.InstituteProfileModifyForm;
import cn.edu.nju.TrainingCollege.domain.InstituteRegisterForm;
import cn.edu.nju.TrainingCollege.entity.Institute;
import cn.edu.nju.TrainingCollege.entity.InstituteApplicationLog;
import cn.edu.nju.TrainingCollege.repository.InstituteApplicationLogRepository;
import cn.edu.nju.TrainingCollege.repository.InstituteRepository;
import cn.edu.nju.TrainingCollege.service.InstituteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author hiki on 2018-01-30
 */

@Service
public class InstituteManager implements InstituteService {

    @Autowired
    private InstituteRepository repository;

    @Autowired
    private InstituteApplicationLogRepository instituteApplicationLogRepository;

    @Autowired
    private EmailHandler emailHandler;

    @Override
    public InstituteApplicationLog register(InstituteRegisterForm form) throws EmailAlreadyExistsException {
        if (isExisted(form.getEmail())) {
            throw new EmailAlreadyExistsException(form.getEmail());
        }
        // generate application log
        InstituteApplicationLog log = InstituteApplicationLog.fromInstituteRegistrationForm(form);
        return instituteApplicationLogRepository.save(log);
    }

    @Override
    public InstituteApplicationLog modifyProfile(Long id, InstituteProfileModifyForm form) {
        Institute institute = find(id);
        InstituteApplicationLog log = InstituteApplicationLog.fromInstituteAndModifyForm(institute, form);
        return instituteApplicationLogRepository.save(log);
    }

    @Override
    public Institute login(InstituteLoginForm form) throws UserNotExistsException, WrongPasswordException {
        Long id = form.getId();
        String password = form.getPassword();
        Institute institute = repository.findOne(id);
        if (institute == null)
            throw new UserNotExistsException();
        if (password == null || !password.equals(institute.getPassword()))
            throw new WrongPasswordException();
        return institute;
    }

    @Override
    public Institute find(Long id) {
        return repository.findOne(id);
    }

    @Override
    public InstituteApplicationLog handleApplication(InstituteApplicationForm form) {
        // save the handle result
        InstituteApplicationLog log = InstituteApplicationLog.fromInstituteApplicationForm(form);
        instituteApplicationLogRepository.save(log);

        // if approved, create or update the institute
        Long instituteId = log.getInstituteId();
        boolean isCreating = instituteId == null;
        Institute institute;
        if (isCreating) {
            institute = repository.save(Institute.fromInstituteApplicationLog(log));
        }
        else {
            // update
            institute = repository.findOne(instituteId).updateFromInstituteApplicationLog(log);
            repository.save(institute);
        }

        // send email to inform institute of the result
        sendHandleResultEmail(log);

        return log;
    }

    @Override
    public List<InstituteApplicationLog> getApplicationLogs() {
        return instituteApplicationLogRepository.findAll();
    }

    @Override
    public InstituteApplicationLog getApplicationLog(Long id) {
        return instituteApplicationLogRepository.findOne(id);
    }

    @Override
    public Integer getInstituteNumber() {
        return repository.findAll().size();
    }


    private void sendHandleResultEmail(InstituteApplicationLog log) {
        boolean isCreating = log.getInstituteId() == null;
        boolean approved = log.getStatus().equals(InstituteApplicationStatus.APPROVED);

        String subject = "机构申请处理结果";
        StringBuilder text = new StringBuilder();
        String op = isCreating ? "注册" : "修改";
        String head = approved ? "您好，" : "抱歉，";
        String tail = approved ? "已通过。" : "未通过。";
        text.append(head).append("您在").append(log.getCreatedAt()).append("的机构信息")
                .append(op).append("操作").append(tail);
        String to = log.getEmail();
        emailHandler.sendSimpleEmail(to, subject, text.toString());
    }

    private boolean isExisted(String email) {
        return repository.findByEmail(email) != null;
    }
}
