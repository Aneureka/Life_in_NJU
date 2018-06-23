package leftovers.service.exceptions;

import com.sun.javaws.exceptions.BadFieldException;

/**
 * Created by kevin on 2017/6/14.
 */
public class BadFieldValueException extends Exception {
    int status;

    public BadFieldValueException(int status, String message){
        super(message);
        this.status = status;
    }

    public int getStatus(){
        return status;
    }
}
