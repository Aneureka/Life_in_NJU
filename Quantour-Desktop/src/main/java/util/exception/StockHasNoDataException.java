package util.exception;

/**
 * Created by kevin on 2017/3/16.
 */
public class StockHasNoDataException extends Exception {
    public StockHasNoDataException() {
    }

    public StockHasNoDataException(String message) {
        super(message);
    }
}
