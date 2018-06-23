package util.enums;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Hiki on 2017/4/15.
 */
public enum UniverseType {

    All,

    Board,

    Self;


    public static UniverseType getUniverseType(String universe){

        // 主要类型
        List<String> mainTypes = Arrays.asList("全部股票", "主板", "中小板", "创业板");


        if (mainTypes.contains(universe)){
            if (universe.equals("全部股票"))
                return All;
            else
                return Board;
        } else {
            return Self;
        }

    }
}
