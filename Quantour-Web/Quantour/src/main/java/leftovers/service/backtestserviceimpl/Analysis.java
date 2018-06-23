package leftovers.service.backtestserviceimpl;

import leftovers.model.backtest.BenchmarkPortfolio;
import leftovers.model.backtest.Portfolio;
import leftovers.util.DataFrame;
import leftovers.util.Series;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 97257 on 2017/4/2.
 */
public class Analysis {

    public static final int STRATEGY = 0;

    public static final int BENCHMARK = 1;

    private PortfolioValue strategy;

    private PortfolioValue benchmark;

    public Analysis(List<Portfolio> portfolios, List<BenchmarkPortfolio> benchmarkPortfolios){
        //初始化strategy
        List<String> table1 = new ArrayList<>();
        //添加表头
        table1.add("Date\tPosition");
        //添加元组
        portfolios.stream().forEach(e -> table1.add(LocalDateTime.ofInstant(Instant.ofEpochMilli(Long.parseLong(e.getDate())), ZoneId.systemDefault()).toLocalDate() + "\t" + e.getTotal_value()));
        this.strategy = new PortfolioValue(new DataFrame(table1.stream().toArray(String[]::new), "\t"));

        //初始化benchmark
        List<String> table2 = new ArrayList<>();
        //添加表头
        table2.add("Date\tPosition");
        //添加元组
        benchmarkPortfolios.stream().forEach(e -> table2.add(LocalDateTime.ofInstant(Instant.ofEpochMilli(Long.parseLong(e.getDate())), ZoneId.systemDefault()).toLocalDate() + "\t" + e.getTotal_value()));
        this.benchmark = new PortfolioValue(new DataFrame(table2.stream().toArray(String[]::new), "\t"));
    }

    public Analysis(PortfolioValue strategy, PortfolioValue benchmark) {
        this.strategy = strategy;
        this.benchmark = benchmark;
    }

    /**
     * 获取Alpha
     *
     * @return Alpha
     */
    public double getAlpha() {
        return strategy.getTotalReturn() - PortfolioValue.riskFreeReturn - getBeta() * (benchmark.getTotalReturn() - PortfolioValue.riskFreeReturn);
    }

    public DataFrame getAlphas() {
        DataFrame strategyTotalReturns = strategy.getTotalReturns();
        DataFrame betas = getBetas();
        DataFrame benchmarkTotalReturns = benchmark.getTotalReturns();
        Double[][] res = new Double[strategyTotalReturns.getHeight()][strategyTotalReturns.getWidth()];

        for (int i = 0; i < res.length; i++) {
            for (int j = 0; j < res[0].length; j++) {
                res[i][j] = ((double)strategyTotalReturns.get(i, j)) - PortfolioValue.riskFreeReturn - ((double)betas.get(i, j)) * (((double)benchmarkTotalReturns.get(i, j)) - PortfolioValue.riskFreeReturn);
            }
        }

        return new DataFrame(res, strategyTotalReturns.getColumns(), strategyTotalReturns.getIndex());
    }

    /**
     * 获取Beta
     *
     * @return Beta
     */
    public double getBeta() {
        Series strategyDailyCumReturn = strategy.getDailyCumReturn();
        Series benchmarkDailyCumReturn = benchmark.getDailyCumReturn();

        return strategyDailyCumReturn.size() == 0 ? 0 : strategyDailyCumReturn
                .allSubtract(strategyDailyCumReturn.average())
                .multiply(benchmarkDailyCumReturn.allSubtract(benchmarkDailyCumReturn.average()))
                .sum() / (strategyDailyCumReturn.size() - 1) / benchmarkDailyCumReturn.variance();
    }

    public DataFrame getBetas() {
        DataFrame strategyDailyCumReturns = strategy.getDailyCumReturns();
        DataFrame benchmarkDailyCumReturns = benchmark.getDailyCumReturns();
        Double[][] res = new Double[strategyDailyCumReturns.getHeight()][strategyDailyCumReturns.getWidth()];

        for (int i = 0; i < res.length; i++) {
            for (int j = 0; j < res[0].length; j++) {
                Series strategyDailyCumReturn = ((Series)strategyDailyCumReturns.get(i, j));
                Series benchmarkDailyCumReturn = ((Series)benchmarkDailyCumReturns.get(i, j));

                res[i][j] = (strategyDailyCumReturns.get(i, j)) == null ?
                            Double.NaN :
                            strategyDailyCumReturn.size() == 0 ? 0 : strategyDailyCumReturn
                                    .allSubtract(strategyDailyCumReturn.average())
                                    .multiply(benchmarkDailyCumReturn.allSubtract(benchmarkDailyCumReturn.average()))
                                    .sum() / (strategyDailyCumReturn.size() - 1) / benchmarkDailyCumReturn.variance();
            }
        }

        return new DataFrame(res, strategyDailyCumReturns.getColumns(), strategyDailyCumReturns.getIndex());
    }

    /**
     * 获取信息比率
     *
     * @return 信息比率
     */
    public double getInformationRatio() {
        Series strategyDailyReturn = strategy.getDailyReturn();
        Series benchmarkDailyReturn = benchmark.getDailyReturn();

        return strategyDailyReturn.size() == 0 ?
                0 :
                244 * strategyDailyReturn.subtract(benchmarkDailyReturn).average() / strategyDailyReturn.subtract(benchmarkDailyReturn).stdev();
    }

    public DataFrame getInformationRatios() {
        DataFrame strategyDailyReturns = strategy.getDailyReturns();
        DataFrame benchmarkDailyReturns = benchmark.getDailyReturns();
        Double[][] res = new Double[strategyDailyReturns.getHeight()][strategyDailyReturns.getWidth()];

        for (int i = 0; i < res.length; i++) {
            for (int j = 0; j < res[0].length; j++) {
                Series strategyDailyReturn = ((Series)strategyDailyReturns.get(i, j));
                Series benchmarkDailyReturn = ((Series)benchmarkDailyReturns.get(i, j));

                res[i][j] = (strategyDailyReturns.get(i, j)) == null ?
                        Double.NaN :
                        strategyDailyReturn.size() == 0 ?
                                0 :
                                244 * strategyDailyReturn.subtract(benchmarkDailyReturn).average() / strategyDailyReturn.subtract(benchmarkDailyReturn).stdev();
            }
        }

        return new DataFrame(res, strategyDailyReturns.getColumns(), strategyDailyReturns.getIndex());
    }

    // 生成最大回撤
    public DataFrame getMaxDrawdowns(int strategyOrbenchmark){
        return strategyOrbenchmark == STRATEGY ?
                strategy.getMaxDrawdowns() :
                benchmark.getMaxDrawdowns();
    }

    // 生成收益波动率
    public DataFrame getVolatilitys(int strategyOrbenchmark){
        return strategyOrbenchmark == STRATEGY ?
                strategy.getVolatilitys() :
                benchmark.getVolatilitys();
    }

    // 生成收益
    public DataFrame getReturns(int strategyOrbenchmark){
        return strategyOrbenchmark == STRATEGY ?
                strategy.getTotalReturns() :
                benchmark.getTotalReturns();
    }

    // 生成夏普比率
    public DataFrame getSharpRatios(int strategyOrbenchmark){
        return strategyOrbenchmark == STRATEGY ?
                strategy.getSharpRatios() :
                benchmark.getSharpRatios();
    }

}

