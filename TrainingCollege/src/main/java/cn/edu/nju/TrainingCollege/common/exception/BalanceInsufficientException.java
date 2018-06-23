package cn.edu.nju.TrainingCollege.common.exception;

/**
 * @author hiki on 2018-04-05
 */

public class BalanceInsufficientException extends Exception {

    public BalanceInsufficientException() {
    }

    public BalanceInsufficientException(String message) {
        super(message);
    }
}
