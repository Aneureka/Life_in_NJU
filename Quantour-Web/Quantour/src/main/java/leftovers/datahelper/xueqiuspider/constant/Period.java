package leftovers.datahelper.xueqiuspider.constant;

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

    public static Period getEnum(String name) {
        for (Period period : Period.values()){
            if (period.toString().equals(name))
                return period;
        }
        throw new IllegalArgumentException("Invalid value:" + name);
    }

}
