package util.tool;

import java.time.LocalDateTime;

/**
 * Created by kevin on 2017/3/9.
 */
public class MyDateTime {

    private final LocalDateTime localDateTime;

    public MyDateTime(LocalDateTime localDateTime){
        this.localDateTime = localDateTime;
    }

    public LocalDateTime asLocalDateTime(){
        return localDateTime;
    }

}