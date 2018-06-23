package bl.backtest.analysis;

import datahelper.utilities.DataFrame;
import datahelper.utilities.Series;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;

/**
 * Created by kevin on 2017/4/16.
 */
public class PortfolioValue {

    /**
     * 回测结果原始数据，用于初始化
     */
    private DataFrame dailyPosition;

    /**
     * 无风险收益
     */
    public static final double riskFreeReturn = 0.04;

    /**
     * 一年里交易日的数量
     */
    private static int annualTradingDays = 250;

    /**
     * 一年的天数（包括非交易日）
     */
    private static double daysOfYear = 365.25;

    public PortfolioValue(DataFrame dailyPosition) {
        this.dailyPosition = dailyPosition;
    }

    /**
     * 获取夏普比率
     *
     * @return 夏普比率
     */
    public double getSharpRatio(){
        return (getAnnualisedReturn() - riskFreeReturn) / (Math.sqrt(annualTradingDays) * getVolatility());
    }

    /**
     * 获取收益波动率
     *
     * @return 收益波动率
     */
    public double getVolatility(){
        return getDailyReturn().stdev();
    }

    /**
     * 获取年化收益率
     *
     * @return 年化收益率
     */
    public double getAnnualisedReturn(){
        return Math.pow(getTotalReturn() + 1, daysOfYear / getBacktestDays()) - 1;
    }

    /**
     * 获取最大回撤
     *
     * @return 最大回撤
     */
    public double getMaxDrawdown(){
        Series positions = dailyPosition.getColumn("Position");
        Series preMaxs = getPreMaxs(positions);
        return positions.divide(preMaxs).allSubtract(1).min();
    }

    /**
     * 获取每日的累计收益率
     *
     * @return 每日的累计收益率
     */
    public Series getDailyCumReturn(){
        Series positions = dailyPosition.getColumn("Position");
        return positions.allDivide((double)positions.get(0)).allSubtract(1);
    }

    /**
     * 获取每日的收益率
     *
     * @return 每日的收益率
     */
    public Series getDailyReturn(){
        Series positions = dailyPosition.getColumn("Position");
        return positions.divide(positions.shift(1)).set(0, 1).allSubtract(1);
    }

    /**
     * 获取总收益
     *
     * @return 总收益
     */
    public double getTotalReturn(){
        Series positions = dailyPosition.getColumn("Position");
        return (double)positions.getLast() / (double)positions.getFirst() - 1;
    }

    public Series getCycleReturn(int refreshRate){
        Series positions = dailyPosition.getColumn("Position");

        //获取周期投资组合价值
        List<Double> cyclePortfolioValueData = new ArrayList<>();
        for (int i = 0; i < positions.size(); i++) {
            if (i % refreshRate == 0 || i == positions.size() - 1){
                cyclePortfolioValueData.add((double)positions.get(i));
            }
        }
        Series cyclePortfolioValue = new Series(cyclePortfolioValueData.toArray());

        return cyclePortfolioValue.divide(cyclePortfolioValue.shift(1)).set(0, 1).allSubtract(1);
    }

    public Series getDates(){
        return dailyPosition.getColumn("Date");
    }

    /**
     * 获取回测的天数（包括了非交易日）
     *
     * @return 回测的天数（包括了非交易日）
     */
    private long getBacktestDays(){
        Series dates = dailyPosition.getColumn("Date");
        return ((LocalDate)dates.getFirst()).until((LocalDate)dates.getLast(), DAYS);
    }

    /**
     * 获取series中每个点之前（包括该点）的最大值
     *
     * @param series the series
     * @return series中每个点之前（包括该点）的最大值
     */
    private Series getPreMaxs(Series series){
        Double[] preMaxs = new Double[series.size()];

        for (int i = 0; i < series.size(); i++) {
            preMaxs[i] = series.get(0, i).max();
        }

        return new Series(preMaxs);
    }

}
