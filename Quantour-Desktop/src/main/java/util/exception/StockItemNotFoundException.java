package util.exception;

import java.io.IOException;

/**
 * Created by 97257 on 2017/4/3.
 */
public class StockItemNotFoundException extends Exception {

    public StockItemNotFoundException(String message, IOException e) {
        super(message, e);
    }

    public StockItemNotFoundException(String message, NullPointerException e) {
        super(message, e);
    }

    public StockItemNotFoundException(String message) {
        super(message);
    }
}
