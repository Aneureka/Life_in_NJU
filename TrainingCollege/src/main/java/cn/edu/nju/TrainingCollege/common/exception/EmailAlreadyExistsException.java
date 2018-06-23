package cn.edu.nju.TrainingCollege.common.exception;

/**
 * @author hiki on 2018-01-24
 */

public class EmailAlreadyExistsException extends Exception {

    public EmailAlreadyExistsException() {}

    public EmailAlreadyExistsException(String message) {
        super(message);
    }

}
