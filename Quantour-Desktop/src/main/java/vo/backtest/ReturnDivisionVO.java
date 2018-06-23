package vo.backtest;

import java.util.List;

/**
 * Created by Hiki on 2017/4/16.
 */
public class ReturnDivisionVO {

    /**
     * 区间长度
     */
    private double bound;


    /**
     * 正收益周期数
     */
    private int opReturns;


    /**
     * 负收益周期数
     */
    private int neReturns;

    /**
     * 赢率
     */
    private double winRate;


    /**
     * 一定区间内正负收益次数
     */
    List<Integer[]> returnNums;



}
