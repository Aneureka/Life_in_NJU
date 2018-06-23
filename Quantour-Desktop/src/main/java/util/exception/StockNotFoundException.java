package util.exception;

import java.io.IOException;

/**
 * Created by Hiki on 2017/3/14.
 */
public class StockNotFoundException extends Exception {

    public StockNotFoundException(String message, IOException e) {
        super(message, e);
    }

    public StockNotFoundException(String message, NullPointerException e) {
        super(message, e);
    }

    public StockNotFoundException(String message) {
        super(message);
    }

}
