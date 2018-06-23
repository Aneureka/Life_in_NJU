package constant;

/**
 * Created by Hiki on 2017/5/14.
 */
public enum Period {

    /**
     * 一天
     */
    DAY("1day"),

    /**
     * 一周
     */
    WEEK("1week"),

    /**
     * 一月
     */
    MONTH("1month");

    private String period;

    private Period(String period){
        this.period = period;
    }

    @Override
    public String toString(){
        return period;
    }
}
