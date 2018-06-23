package vo.backtest;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by kevin on 2017/4/17.
 */
public class DailyCumReturnVO {

    /**
     * 日期
     */
    public List<LocalDate> dates;

    /**
     * 策略累计收益
     */
    public List<Double> strategyDailyCumReturn;

    /**
     * 基准累计收益
     */
    public List<Double> benchmarkDailyCumReturn;


    public DailyCumReturnVO(List<LocalDate> dates, List<Double> strategyDailyCumReturn, List<Double> benchmarkDailyCumReturn) {
        this.dates = dates;
        this.strategyDailyCumReturn = strategyDailyCumReturn;
        this.benchmarkDailyCumReturn = benchmarkDailyCumReturn;
    }

}
