package bl.backtest.analysis;

import bl.backtest.BackTest;
import datahelper.utilities.DataFrame;
import datahelper.utilities.Series;
import util.tool.DistributionAnalyse;
import vo.backtest.*;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.time.temporal.ChronoUnit.DAYS;

/**
 * Created by 97257 on 2017/4/2.
 */
public class Analysis {

    private PortfolioValue strategy;

    private PortfolioValue benchmark;

    public Analysis(List<PriceVO> priceVOS){
        //初始化strategy
        List<String> table1 = new ArrayList<>();
        //添加表头
        table1.add("Date\tPosition");
        //添加元组
        priceVOS.stream().forEach(e -> table1.add(e.getDate() + "\t" + e.getAccount()));
        this.strategy = new PortfolioValue(new DataFrame(table1.stream().toArray(String[]::new), "\t"));

        //初始化benchmark
        List<String> table2 = new ArrayList<>();
        //添加表头
        table2.add("Date\tPosition");
        //添加元组
        priceVOS.stream().forEach(e -> table2.add(e.getDate() + "\t" + e.getBenchmark()));
        this.benchmark = new PortfolioValue(new DataFrame(table2.stream().toArray(String[]::new), "\t"));
    }

    public Analysis(PortfolioValue strategy, PortfolioValue benchmark) {
        this.strategy = strategy;
        this.benchmark = benchmark;
    }

    public BacktestResultOverviewVO getOverview(){
        //设置各个属性值
        double sharpRatio = strategy.getSharpRatio();
        double volatility = strategy.getVolatility();
        double strategyAnnualisedReturn = strategy.getAnnualisedReturn();
        double benchmarkAnnualiseReturn = benchmark.getAnnualisedReturn();
        double alpha = getAlpha();
        double beta = getBeta();
        double maxDrawdown = strategy.getMaxDrawdown();
        double totalReturn = strategy.getTotalReturn();
        List<LocalDate> dates = Arrays.stream(strategy.getDates().toArray()).map(date -> (LocalDate)date).collect(Collectors.toList());
        List<Double> strategyDailyCumReturn = Arrays.stream(strategy.getDailyCumReturn().toArray()).map(num -> (double)num).collect(Collectors.toList());
        List<Double> benchmarkDailyCumReturn = Arrays.stream(benchmark.getDailyCumReturn().toArray()).map(num -> (double)num).collect(Collectors.toList());
        DailyCumReturnVO dailyCumReturn = new DailyCumReturnVO(dates, strategyDailyCumReturn, benchmarkDailyCumReturn);

        return new BacktestResultOverviewVO(sharpRatio, volatility, strategyAnnualisedReturn, benchmarkAnnualiseReturn, alpha, beta, maxDrawdown, totalReturn, dailyCumReturn);
    }

    public WinRateVO getWinRate(int refreshRate){
        //设置各个属性值
        double CAR = getCAR();
        double winRate = getWR(refreshRate);

        return new WinRateVO(CAR, winRate);
    }

    public ReturnDistributionVO getReturnDistribution(int refreshRate){
        Series cycleReturn = strategy.getCycleReturn(refreshRate);

        //设置各个属性值
        int positiveReturnNum = (int) Arrays.stream(cycleReturn.toArray()).filter(num -> (double)num > 0).count();
        int negativeReturnNum = (int) Arrays.stream(cycleReturn.toArray()).filter(num -> (double)num < 0).count();
        double winRate = positiveReturnNum + negativeReturnNum == 0 ? 0 : positiveReturnNum / (double)(positiveReturnNum + negativeReturnNum);
        double lowerBound = -(int)((Math.max(cycleReturn.max(), Math.abs(cycleReturn.min())) + 0.01) * 100) / (double)100;
        double upperBound = -lowerBound;
        int cut = (int)(upperBound * 100);
        double[] cutPoints = DistributionAnalyse.getCutPoints(0, upperBound, cut);
        List<Double> cycleReturnData = Arrays.stream(cycleReturn.toArray()).map(num -> (double)num).filter(num -> num != 0).collect(Collectors.toList());
        int[] positiveDistribution = DistributionAnalyse.analyse(cycleReturnData, 0, upperBound, cut);
        int[] negativeDistribution = DistributionAnalyse.analyse(cycleReturnData, lowerBound, 0, cut);;

        return new ReturnDistributionVO(positiveReturnNum, negativeReturnNum, winRate, cutPoints, positiveDistribution, negativeDistribution);
    }

    /**
     * 获取Alpha
     *
     * @return Alpha
     */
    public double getAlpha(){
        return strategy.getTotalReturn() - PortfolioValue.riskFreeReturn - getBeta() * (benchmark.getTotalReturn() - PortfolioValue.riskFreeReturn);
    }

    /**
     * 获取Beta
     *
     * @return Beta
     */
    public double getBeta(){
        Series strategyDailyCumReturn = strategy.getDailyCumReturn();
        Series benchmarkDailyCumReturn = benchmark.getDailyCumReturn();

        return strategyDailyCumReturn.size() == 0 ? 0 : strategyDailyCumReturn
                .allSubtract(strategyDailyCumReturn.average())
                .multiply(benchmarkDailyCumReturn.allSubtract(benchmarkDailyCumReturn.average()))
                .sum() / (strategyDailyCumReturn.size() - 1) / benchmarkDailyCumReturn.variance();
    }

    public double getCAR(){
        return strategy.getTotalReturn() - benchmark.getTotalReturn();
    }

    public double getWR(int refreshRate){
        Series cycleCAR = strategy.getCycleReturn(refreshRate).subtract(benchmark.getCycleReturn(refreshRate));

        int positiveReturnNum = (int) Arrays.stream(cycleCAR.toArray()).filter(num -> (double)num > 0).count();
        int negativeReturnNum = (int) Arrays.stream(cycleCAR.toArray()).filter(num -> (double)num < 0).count();
        double winRate = positiveReturnNum + negativeReturnNum == 0 ? 0 : positiveReturnNum / (double)(positiveReturnNum + negativeReturnNum);

        return positiveReturnNum + negativeReturnNum == 0 ? 0 : positiveReturnNum / (double)(positiveReturnNum + negativeReturnNum);
    }

}

