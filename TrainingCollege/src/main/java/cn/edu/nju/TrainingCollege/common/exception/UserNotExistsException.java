package cn.edu.nju.TrainingCollege.common.exception;

/**
 * @author hiki on 2018-01-24
 */

public class UserNotExistsException extends Exception {


    public UserNotExistsException() {}

    public UserNotExistsException(String message) {
        super(message);
    }
}
