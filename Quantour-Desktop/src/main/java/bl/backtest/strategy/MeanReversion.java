package bl.backtest.strategy;

import bl.backtest.account.Account;
import dataservice.backtestdata.BacktestDataService;
import util.enums.Benchmark;
import util.exception.StockItemNotFoundException;
import util.exception.StockNotFoundException;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by 97257 on 2017/4/2.
 */
public class MeanReversion extends Strategy{

    /**
     * 构造函数
     */
    public MeanReversion(LocalDate beginTime, LocalDate endTime, Map<LocalDate, Double> benchmarkData, Account account, int refreshRate, int pma) throws StockNotFoundException {
        super(beginTime, endTime, benchmarkData, account, refreshRate, pma);
    }

    /**
     * 重载父类的方法
     */
    @Override
    public void handleData(LocalDate day) {
        // 确定股票池
        List<String> universe = account.getUniverse();
//        System.out.println("股票池大小为 " + universe.size());

        // 获取股票池内的股票前pma天的数据
        Map<String, Double[]> stockPrices = backtestDs.getListStockPrice(universe, day, formRate) ;
        // 获得每个股票的偏离度（5天、10天、20天）
        List<Deviation> deviations = getDeviations(stockPrices);
        // 按偏离度从高到低排序
        deviations.sort((a, b) -> a.deviationRate > b.deviationRate ? 1 : a.deviationRate == b.deviationRate ? 0 : -1);
        // 取前20%，并去除涨停的股票
        List<String> buylist = deviations.subList(0, deviations.size()/5).stream()
                .map(e -> e.stock)
                .filter(e -> !account.isLimitUp(e, day))
                .collect(Collectors.toList());

        // 确认buylist之后执行买卖操作
        order(buylist, day, stockPrices);

    }


    /**
     * 获得妹纸股票的偏离度
     */
    private List<Deviation> getDeviations(Map<String, Double[]> stockPrices){
        List<Deviation> deviations = new ArrayList<>();

        // 遍历map，并加到returns中
        Iterator<Map.Entry<String, Double[]>> entries = stockPrices.entrySet().iterator();

        while (entries.hasNext()) {
            Map.Entry<String, Double[]> entry = entries.next();
            String code = entry.getKey();
            Double[] prices = entry.getValue();
            // 获取日线均值
            double mean = Arrays.stream(prices).mapToDouble(Double::new).average().orElse(0);
            double deviationRate = (mean - prices[0]) / mean;
            deviations.add(new Deviation(code, deviationRate));
        }

        return deviations;
    }


    // 偏离度
    class Deviation{

        String stock;

        double deviationRate;

        Deviation(String stock, double deviationRate) {
            this.stock = stock;
            this.deviationRate = deviationRate;
        }
    }

}