package leftovers.service;

import com.sun.javaws.exceptions.BadFieldException;
import com.sun.javaws.exceptions.ExitException;
import leftovers.controller.support.ChangePasswordForm;
import leftovers.model.User;
import leftovers.model.UserRole;
import leftovers.model.WatchlistItem;
import leftovers.repository.UserRepository;
import leftovers.repository.WatchListRepository;
import leftovers.service.exceptions.BadFieldValueException;
import leftovers.util.FormatChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.management.BadAttributeValueExpException;
import javax.management.relation.Role;
import javax.ws.rs.NotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kevin on 2017/5/16.
 */
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WatchListRepository watchListRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public void checkRegisterInfoForTest(User user) throws BadFieldValueException {
        if (user.getUsername() == null && user.getEmailAddress() == null && user.getPhoneNumber() == null)
            throw new BadFieldValueException(504, "Null value.");

        //检查用户名是否已被注册
        if (user.getUsername() != null){
            User userFromDB = userRepository.findByUsername(user.getUsername());
            if (userFromDB != null)
                throw new BadFieldValueException(501, "Username has been taken.");
        }

        //检查邮箱是否被使用以及格式是否正确
        if (user.getEmailAddress() != null){
            List<User> userFromDB1 = userRepository.findByEmailAddress(user.getEmailAddress());
            if (userFromDB1.size() != 0)
                throw new BadFieldValueException(502, "Email address has been taken.");
            if (!FormatChecker.check(FormatChecker.REGEX_EMAIL, user.getEmailAddress()))
                throw new BadFieldValueException(505, "Email format error.");
        }

        //检查手机号是否被使用以及格式是否正确
        if (user.getPhoneNumber() != null){
            List<User> userFromDB2 = userRepository.findByPhoneNumber(user.getPhoneNumber());
            if (userFromDB2.size() != 0)
                throw new BadFieldValueException(503, "Phone number has been taken.");
            if (!FormatChecker.check(FormatChecker.REGEX_MOBILE, user.getPhoneNumber()))
                throw new BadFieldValueException(506, "Phone format error.");
        }
    }

    public void checkRegisterInfo(User user) throws BadFieldValueException {
        if (user.getUsername() == null)
            throw new BadFieldValueException(504, "Username null.");

        if (user.getEmailAddress() == null)
            throw new BadFieldValueException(505, "Email address null.");

        if (user.getPhoneNumber() == null)
            throw new BadFieldValueException(506, "Phone number null.");

        if (user.getPassword() == null)
            throw new BadFieldValueException(507, "Phone number null.");

        //用于检查用户名是否已被注册
        User userFromDB = userRepository.findByUsername(user.getUsername());
        //用于检查邮箱是否被使用
        List<User> userFromDB1 = userRepository.findByEmailAddress(user.getEmailAddress());
        //用于检查手机号是否被使用
        List<User> userFromDB2 = userRepository.findByPhoneNumber(user.getPhoneNumber());

        if (userFromDB != null)
            throw new BadFieldValueException(501, "Username has been taken.");

        if (userFromDB1.size() != 0)
            throw new BadFieldValueException(502, "Email address has been taken.");

        if (userFromDB2.size() != 0)
            throw new BadFieldValueException(503, "Phone number has been taken.");

        if (!FormatChecker.check(FormatChecker.REGEX_EMAIL, user.getEmailAddress()))
            throw new BadFieldValueException(508, "Email format error.");

        if (!FormatChecker.check(FormatChecker.REGEX_MOBILE, user.getPhoneNumber()))
            throw new BadFieldValueException(509, "Phone format error.");
    }

    public User createUser(User user) throws BadFieldValueException {
        checkRegisterInfo(user);

        List<UserRole> roles = new ArrayList<>();
        UserRole role = new UserRole();
        role.setRole(UserRole.DEFAULT_ROLE);
        role.setUser(user);
        roles.add(role);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnabled(1);
        user.setRoles(roles);
        return userRepository.saveAndFlush(user);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public void changePassword(ChangePasswordForm changePasswordForm) {
        User userFromDB = userRepository.findByUsername(changePasswordForm.getUsername());

        if (userFromDB == null)
            throw new NotFoundException("用户不存在或密码错误");

        if (!passwordEncoder.matches(changePasswordForm.getPassword(), userFromDB.getPassword()))
            throw new NotFoundException("用户不存在或密码错误");

        userRepository.updatePassword(changePasswordForm.getUsername(), passwordEncoder.encode(changePasswordForm.getNewPassword()));
    }

    public void forgetPassword(ChangePasswordForm changePasswordForm) {
        User userFromDB = userRepository.findByUsername(changePasswordForm.getUsername());

        if (userFromDB == null)
            throw new NotFoundException("用户不存在或手机号错误");

        if (!changePasswordForm.getPhoneNumber().equals(userFromDB.getPhoneNumber()))
            throw new NotFoundException("用户不存在或手机号错误");

        userRepository.updatePassword(changePasswordForm.getUsername(), passwordEncoder.encode(changePasswordForm.getNewPassword()));
    }

    public List<WatchlistItem> getWatchlist(String username) {
        return watchListRepository.findByUsername(username);
    }

    public WatchlistItem addWatchlistItem(String username, String code) {
        //用于检查股票是否已添加为自选股
        WatchlistItem watchlistItemFromDB = watchListRepository.findByUsernameAndCode(username, code);

        if (watchlistItemFromDB == null) {
            WatchlistItem watchlistItem = new WatchlistItem();

            watchlistItem.setUsername(username);
            watchlistItem.setCode(code);

            return watchListRepository.saveAndFlush(watchlistItem);
        } else {
            return null;
        }
    }

    public List<WatchlistItem> removeWatchlistItem(String username, String code) {
        return watchListRepository.deleteByUsernameAndCode(username, code);
    }


}
