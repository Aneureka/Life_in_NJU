package bl.backtest.strategy;

import bl.backtest.account.Account;
import com.sun.org.apache.regexp.internal.RE;
import dataservice.backtestdata.BacktestDataService;
import util.enums.Benchmark;
import util.exception.StockItemNotFoundException;
import util.exception.StockNotFoundException;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Hiki on 2017/4/2.
 */
public class Momentum extends Strategy {

    /**
     * 构造函数
     */
    public Momentum(LocalDate beginTime, LocalDate endTime, Map<LocalDate, Double> benchmarkData, Account account, int refreshRate, int formRate) throws StockNotFoundException {
        super(beginTime, endTime, benchmarkData, account, refreshRate, formRate);
    }

    /**
     * 重载父类的方法
     */
    @Override
    public void handleData(LocalDate day) {

        // 确定股票池
        List<String> universe = account.getUniverse();

        // 获取股票池内的股票前formRate天的数据，已经排除了停牌的股票
        Map<String, Double[]> stockPrices = backtestDs.getListStockPrice(universe, day, formRate);

        // 获取股票池内前formRate天的收益率
        List<Return> returns = getReturns(stockPrices);

        // 对收益率从高到低排序
        returns.sort((a, b) -> a.returnRate > b.returnRate ? 1 : (a.returnRate == b.returnRate ? 0 : -1));

        // 取前20%作为赢家组合，并去除涨停的股票
        List<String> buylist = returns.subList(0, returns.size()/5).stream()
                .map(e -> e.stock)
                .filter(e -> !account.isLimitUp(e, day))
                .collect(Collectors.toList());

        // 确认buylist之后执行买卖操作
        order(buylist, day, stockPrices);

    }


    /**
     * 获得股票池内各只股票的收益率
     */
    private List<Return> getReturns(Map<String, Double[]> stockPrices) {

        List<Return> returns = new ArrayList<>();

        // 遍历map，并加到returns中
        Iterator<Map.Entry<String, Double[]>> entries = stockPrices.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry<String, Double[]> entry = entries.next();
            String code = entry.getKey();
            Double[] prices = entry.getValue();
            double returnRate = (prices[0] - prices[prices.length - 1]) / prices[prices.length - 1];
            returns.add(new Return(code, returnRate));
        }

        return returns;
    }
    class Return {

        String stock;

        double returnRate;

        Return(String stock, double returnRate) {
            this.stock = stock;
            this.returnRate = returnRate;
        }
    }
}