package leftovers.service.backtestserviceimpl;

import leftovers.util.DataFrame;
import leftovers.util.Series;

import java.time.LocalDate;

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

    public DataFrame getSharpRatios(){
        DataFrame portfolioValues = twoDimensionSplit();
        Double[][] res = new Double[portfolioValues.getHeight()][portfolioValues.getWidth()];

        for (int i = 0; i < res.length; i++) {
            for (int j = 0; j < res[0].length; j++) {
                res[i][j] = portfolioValues.get(i, j) == null ?
                            Double.NaN :
                            ((PortfolioValue)portfolioValues.get(i, j)).getSharpRatio();
            }
        }

        return new DataFrame(res, portfolioValues.getColumns(), portfolioValues.getIndex());
    }

    /**
     * 获取收益波动率
     *
     * @return 收益波动率
     */
    public double getVolatility(){
        return getDailyReturn().stdev();
    }

    public DataFrame getVolatilitys(){
        DataFrame portfolioValues = twoDimensionSplit();
        Double[][] res = new Double[portfolioValues.getHeight()][portfolioValues.getWidth()];

        for (int i = 0; i < res.length; i++) {
            for (int j = 0; j < res[0].length; j++) {
                res[i][j] = portfolioValues.get(i, j) == null ?
                        Double.NaN :
                        ((PortfolioValue)portfolioValues.get(i, j)).getVolatility();
            }
        }

        return new DataFrame(res, portfolioValues.getColumns(), portfolioValues.getIndex());
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

    public DataFrame getMaxDrawdowns(){
        DataFrame portfolioValues = twoDimensionSplit();
        Double[][] res = new Double[portfolioValues.getHeight()][portfolioValues.getWidth()];

        for (int i = 0; i < res.length; i++) {
            for (int j = 0; j < res[0].length; j++) {
                res[i][j] = portfolioValues.get(i, j) == null ?
                        Double.NaN :
                        ((PortfolioValue)portfolioValues.get(i, j)).getMaxDrawdown();
            }
        }

        return new DataFrame(res, portfolioValues.getColumns(), portfolioValues.getIndex());
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

    public DataFrame getDailyCumReturns(){
        DataFrame portfolioValues = twoDimensionSplit();
        Series[][] res = new Series[portfolioValues.getHeight()][portfolioValues.getWidth()];

        for (int i = 0; i < res.length; i++) {
            for (int j = 0; j < res[0].length; j++) {
                res[i][j] = portfolioValues.get(i, j) == null ?
                        null :
                        ((PortfolioValue)portfolioValues.get(i, j)).getDailyCumReturn();
            }
        }

        return new DataFrame(res, portfolioValues.getColumns(), portfolioValues.getIndex());
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

    public DataFrame getDailyReturns(){
        DataFrame portfolioValues = twoDimensionSplit();
        Series[][] res = new Series[portfolioValues.getHeight()][portfolioValues.getWidth()];

        for (int i = 0; i < res.length; i++) {
            for (int j = 0; j < res[0].length; j++) {
                res[i][j] = portfolioValues.get(i, j) == null ?
                        null :
                        ((PortfolioValue)portfolioValues.get(i, j)).getDailyReturn();
            }
        }

        return new DataFrame(res, portfolioValues.getColumns(), portfolioValues.getIndex());
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

    public DataFrame getTotalReturns(){
        DataFrame portfolioValues = twoDimensionSplit();
        Double[][] res = new Double[portfolioValues.getHeight()][portfolioValues.getWidth()];

        for (int i = 0; i < res.length; i++) {
            for (int j = 0; j < res[0].length; j++) {
                res[i][j] = portfolioValues.get(i, j) == null ?
                        Double.NaN :
                        ((PortfolioValue)portfolioValues.get(i, j)).getTotalReturn();
            }
        }

        return new DataFrame(res, portfolioValues.getColumns(), portfolioValues.getIndex());
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

    private DataFrame twoDimensionSplit(){
        //计算出总共有多少个月，作为分割的第一个维度
        LocalDate startDate = (LocalDate) dailyPosition.getColumn("Date").getFirst();
        LocalDate endDate = (LocalDate) dailyPosition.getColumn("Date").getLast();
        int firstDimension = endDate.getMonthValue() - startDate.getMonthValue() + 1 + (endDate.getYear() - startDate.getYear()) * 12;
        LocalDate[] index = new LocalDate[firstDimension];
        for (int i = 0; i < index.length; i++) {
            index[i] = startDate.withDayOfMonth(1).plusMonths(i);
        }

        //第二个维度为固定值
        int secondDimension = 4;
        int[] months = {1, 3, 6, 12};
        String[] columns = {"date", "one_month", "three_months", "six_months", "twelve_months"};

        //进行分割
        PortfolioValue[][] portfolioValues = new PortfolioValue[firstDimension][secondDimension];
        for (int i = 0; i < firstDimension; i++) {
            //获取一维数据起始点lowerIndex1，upperIndex1
            LocalDate lowerBound1 = startDate.withDayOfMonth(1);
            LocalDate upperBound1 = startDate.plusMonths(i + 1).withDayOfMonth(1);
            int lowerIndex1 = 0;
            int upperIndex1 = 0;
            Series dates = dailyPosition.getColumn("Date");
            for (int j = 0; j < dates.size(); j++) {
                LocalDate cur = (LocalDate)(dates.get(j));
                if ((cur.isEqual(lowerBound1) || cur.isAfter(lowerBound1)) && cur.isBefore(upperBound1)){
                    upperIndex1 = j;
                }
            }

            for (int j = 0; j < secondDimension; j++) {
                if (months[j] > (i + 1)){
                    portfolioValues[i][j] = null;
                }else {
                    //获取二维数据起始点lowerIndex2，upperIndex2
                    LocalDate lowerBound2 = upperBound1.minusMonths(months[j]);
                    LocalDate upperBound2 = upperBound1;
                    int lowerIndex2 = 0;
                    int upperIndex2 = upperIndex1;
                    for (int k = 0; k < dates.size(); k++) {
                        LocalDate cur = (LocalDate)(dates.get(k));
                        if ((cur.isEqual(lowerBound2) || cur.isAfter(lowerBound2)) && cur.isBefore(upperBound2)){
                            lowerIndex2 = k;
                            break;
                        }
                    }

                    portfolioValues[i][j] = new PortfolioValue(dailyPosition.rowSlice(lowerIndex2, upperIndex2 + 1));
                }
            }
        }

        return new DataFrame(portfolioValues, columns, index);
    }

}
