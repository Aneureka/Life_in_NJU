package bl.backtest;

import bl.backtest.account.Account;
import bl.backtest.analysis.Analysis;
import bl.backtest.strategy.MeanReversion;
import bl.backtest.strategy.Momentum;
import bl.backtest.strategy.Strategy;
import blservice.backtestblservice.BackTestBLService;
import dao.backtest.BackTestDao;
import dataservice.backtestdata.BacktestDataService;
import util.enums.Benchmark;
import util.enums.Board;
import util.enums.RateType;
import util.enums.UniverseType;
import util.exception.StockNotFoundException;
import vo.backtest.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;

/**
 * Created by 97257 on 2017/4/2.
 * 实现对外的接口，供backtestcontroller调用
 */
public class BackTest implements BackTestBLService{

    private BacktestDataService backtestDs;

    private Account account;

    private Analysis analysis;

    private Strategy strategy;

    /**
     * 构造函数
     * @throws StockNotFoundException
     */
    public BackTest() throws StockNotFoundException {
        this.backtestDs = new BackTestDao();

    }

    @Override
    public void runMomentum(LocalDate beginTime, LocalDate endTime, Benchmark benchmark, String[] universeNames, double capitalBase, int formRate, int refreshRate) throws StockNotFoundException {

        // 初始化
        account = new Account(capitalBase, findUniverse(universeNames));
        strategy = new Momentum(beginTime, endTime,
                getBenchmarkData(beginTime, endTime, benchmark, universeNames),
                this.account, refreshRate, formRate);

        // 执行策略，此时strategy里面的prices已经拥有数据
        strategy.execute();

        // 用prices数据构造analysis对象
        analysis = new Analysis(strategy.getPrices());

    }

    @Override
    public void runMeanRevision(LocalDate beginTime, LocalDate endTime, Benchmark benchmark, String[] universeNames, double capitalBase, int pma, int refreshRate) throws StockNotFoundException {

        // 初始化
        account = new Account(capitalBase, findUniverse(universeNames));
        strategy = new MeanReversion(beginTime, endTime,
                getBenchmarkData(beginTime, endTime, benchmark, universeNames),
                this.account, refreshRate, pma);

        // 执行策略，此时strategy里面的prices已经拥有数据
        strategy.execute();

        // 用prices数据构造analysis对象
        analysis = new Analysis(strategy.getPrices());

    }

    @Override
    public BacktestResultOverviewVO getBacktestResultOverview() {
        return analysis.getOverview();
    }


    @Override
    public List<ChangingRateVO> getChangingWP(RateType rateType, int rate) {

        // 根据传入的形成期/持有期设置strategy相应的值
        if (rateType.equals(RateType.formRate))
            strategy.setFormRate(rate);
        else
            strategy.setRefreshRate(rate);

        // 确定回测区间的大小，与80（默认值）做比较
        int tradedays = backtestDs.getTradeDays(strategy.getBeginTime(), strategy.getEndTime()).size();
        int interval = tradedays < 80 ? tradedays : 80;

        // 逐个添加
        List<ChangingRateVO> changingRates = new ArrayList<>();

        // 遍历另外一个rate属性
        for (int i = 2; i < interval; i += 2) {
            if (rateType.equals(RateType.formRate))
                strategy.setRefreshRate(i);
            else
                strategy.setFormRate(i);
            strategy.execute();
            analysis = new Analysis(strategy.getPrices());
            WinRateVO winRate = analysis.getWinRate(strategy.getRefreshRate());
            changingRates.add(new ChangingRateVO(i, winRate.CAR, winRate.winRate));
        }

        return changingRates;
    }

    @Override
    public ReturnDistributionVO getReturns() {
        return analysis.getReturnDistribution(strategy.getRefreshRate());
    }

    @Override
    public List<PositionRecordVO> getPositionRecord() {
        return account.getPositionRecords();
    }


    /**
     * 根据取得基准类型（及选定的股票池）得到基准一段时间内的数据
     * @return
     */
    private TreeMap<LocalDate, Double> getBenchmarkData(LocalDate beginTime, LocalDate endTime, Benchmark benchmark, String[] universeNames){

        List<String> universe = findUniverse(universeNames);

        // 如果是自选股票池
        if (isSelf(universeNames[0])) {
            return backtestDs.getAveragePricesByCodeList(universe, beginTime, endTime);
        }

        // 正常情况
        return backtestDs.getPricesByBenchmark(benchmark, beginTime, endTime);

    }


    /**
     * 根据传进来的股票池名称获得股票池的股票列表
     * @return
     */
    private List<String> findUniverse(String[] universeNames){

        List<String> universe = new ArrayList<>();

        for (int i = 0; i < universeNames.length; i++){

            UniverseType universeType = UniverseType.getUniverseType(universeNames[i]);

            switch (universeType){
                case All:
                    universe.addAll(backtestDs.getAllStockCodes());
                    break;
                case Board:
                    universe.addAll(backtestDs.getStockCodesByBoard(Board.getBoard(universeNames[i])));
                    break;
                default:
                    universe.addAll(backtestDs.getStockCodesByUniverseName(universeNames[i]));
                    break;
            }

        }

        return universe;

    }


    /**
     * 判断是不是自选的股票
     * @param universe
     * @return
     */
    private boolean isSelf(String universe){
        return UniverseType.getUniverseType(universe).equals(UniverseType.Self);
    }

}