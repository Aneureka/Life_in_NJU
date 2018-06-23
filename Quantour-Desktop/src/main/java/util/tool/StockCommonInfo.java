package util.tool;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hiki on 2017/3/13.
 */
public class StockCommonInfo {

    /**
     * 均线图的天数
     */
    public final static List<Integer> AVERAGE_DAY;

    static {
        AVERAGE_DAY = new ArrayList<>();
        AVERAGE_DAY.add(5);
        AVERAGE_DAY.add(10);
        AVERAGE_DAY.add(30);
        AVERAGE_DAY.add(60);
        AVERAGE_DAY.add(120);
        AVERAGE_DAY.add(240);
}

}
