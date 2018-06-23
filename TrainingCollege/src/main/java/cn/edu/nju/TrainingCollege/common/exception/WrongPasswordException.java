package cn.edu.nju.TrainingCollege.common.exception;

/**
 * @author hiki on 2018-01-26
 */

public class WrongPasswordException extends Exception {

    public WrongPasswordException() {}

    public WrongPasswordException(String message) {
        super(message);
    }
}
