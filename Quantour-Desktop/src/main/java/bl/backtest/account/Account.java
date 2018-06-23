package bl.backtest.account;

import dao.backtest.BackTestDao;
import dataservice.backtestdata.BacktestDataService;
import util.enums.TradeType;
import util.exception.StockItemNotFoundException;
import util.exception.StockNotFoundException;
import vo.backtest.PositionRecordVO;
import vo.backtest.StockHoldingVO;

import java.time.LocalDate;
import java.util.*;

/**
 * Created by 97257 on 2017/4/2.
 */
public class Account {

    /**
     * 当前剩余资金
     */
    private double capitalPresent;

    /**
     * 当前持有的股票
     */
    private Map<String, Integer> stockHoldings;

    /**
     * 选择的股票池
     */
    private List<String> universe;

    /**
     * 持仓记录
     */
    private List<PositionRecordVO> positionRecords;

    /**
     * 数据层对象
     */
    private BacktestDataService backtestDataService;

    /**
     * 构造函数，只需要提供起始资金
     * @param capitalBase
     */
    public Account(double capitalBase, List<String> universe) throws StockNotFoundException {
        this.capitalPresent = capitalBase;
        this.universe = universe;
        this.stockHoldings = new HashMap<>();
        this.positionRecords = new ArrayList<>();
        this.backtestDataService = new BackTestDao();
    }

    /**
     * 将股票进行买卖至剩余一定数量，Num为0时表示卖出全部
     * @param code
     * @param num
     */
    public void order_to(String code, int num, LocalDate date){
        // 如果不能进行买卖操作（停牌/涨跌停），则略过这只股票
        if (!canTrade(code, num, date))
            return;

        // 正常情况
        // 取出持有股票中要进行交易的股票的数量
        int stockNumInStockHoldings = getStockNumInStockHoldings(code);
        // 计算差价 买入为+ 卖出为-
        double diff = (num - stockNumInStockHoldings) * getStockPresentPrice(code, date);
        // 当持有现金大于差价时，才可以进行买入
        if (this.capitalPresent >= diff){
            // 更新持有股票列表
            updateStockHoldings(code, num);
            // 更新现金
            this.capitalPresent -= diff;
        }

    }



    /**
     * 产生新的交易日志条目，加到交易记录里面
     * @param date 交易时间
     */
    public void appendPositionRecord(LocalDate date) {

        // 构造持有股票信息
        List<StockHoldingVO> stockHoldingVOs = new ArrayList<>();
        // 遍历持有股票
        for (Map.Entry<String, Integer> entry : stockHoldings.entrySet()) {
            String code = entry.getKey();
            String name = backtestDataService.getStockName(code);
            int num = entry.getValue();
            double position = 0.0;
            try {
                position = backtestDataService.getStockLastPriceFromMemory(code, date) * num;
            } catch (StockItemNotFoundException e) {
               // System.out.println(code + "没有最近一天的价格！");
            }

            stockHoldingVOs.add(new StockHoldingVO(code, name, num, position));
        }

        // 获得总资产盈亏
        double allProfit = 0.0;
        if (!positionRecords.isEmpty()){
            double lastAllProfit = positionRecords.get(positionRecords.size()-1).getCapitalAll();
            allProfit = this.getCapitalAll(date) - lastAllProfit;
        }

        this.positionRecords.add(new PositionRecordVO(date, this.getCapitalAll(date), this.getCapitalPresent(), allProfit, stockHoldingVOs));

    }


    /**
     * 获得当前可用于买入股票的总资产
     */
    public double getCapitalAvailable(LocalDate date){
        // 初始化总资产 = 现金
        double capitalAll = capitalPresent;

        // 加入当前持有的股票的总价值
        Iterator<Map.Entry<String, Integer>> entries = this.stockHoldings.entrySet().iterator();
        while(entries.hasNext()){
            Map.Entry<String, Integer> entry = entries.next();
            String code = entry.getKey();
            int num = entry.getValue();
            // 若股票停牌或跌停，则不能加入可用的总资产
            if (!isSuspended(code, date) && !isLimitDown(code, date))
                capitalAll += getStockPresentPrice(code, date) * num;
        }

        return capitalAll;
    }


    /**
     * 获得当前总资产（现金+股票总价值）
     * @param date
     * @return
     */
    public double getCapitalAll(LocalDate date){
        // 初始化总资产 = 现金
        double capitalAll = capitalPresent;

        // 加入当前持有的股票的总价值
        for (Map.Entry<String, Integer> entry : this.stockHoldings.entrySet()) {
            capitalAll += getStockPresentPrice(entry.getKey(), date) * entry.getValue();
        }

        return capitalAll;

    }

    /**
     * 获得停牌/跌停股票数量
     * @param date
     * @return
     */
    public int getSuspendedStockNum(LocalDate date){
        int result = 0;
        Iterator<String> codes = stockHoldings.keySet().iterator();
        while (codes.hasNext()){
            String code = codes.next();
            if (isSuspended(code, date) || isLimitDown(code, date))
                result++;
        }
        return result;
    }


    /**
     * 获得持有股票列表
     * @return
     */
    public List<String> getStockHoldingList(){

        List<String> stockHoldingList = new ArrayList<>();

        stockHoldingList.addAll(this.stockHoldings.keySet());

        return stockHoldingList;
    }





    /**
     * 判断某股票在特定交易日是否涨停 公式：(C-C1)*100/C1 >= (10-0.01*100/C1)，ST股需要特别对待
     * @param code
     * @param date
     * @return
     */
    public boolean isLimitUp(String code, LocalDate date){
        try {
            double[] stockLastTwoDayPrice = backtestDataService.getStockPrice(code, date, 2);
            double c1 = stockLastTwoDayPrice[0];
            double c = stockLastTwoDayPrice[1];

            // 判断
            if (isSTStock(code))
                return (c - c1) * 100 / c1 >= (5 - 0.01 * 100 / c1);
            else
                return (c - c1) * 100 / c1 >= (10 - 0.01 * 100 / c1);

        } catch (StockItemNotFoundException e) {
            return false;
        }

    }


    /**
     * 判断某股票在特定交易日是否停牌（通过判断当天有没有价格数据）
     * @param code
     * @param date
     * @return
     */
    private boolean isSuspended(String code, LocalDate date){
        try {
            double stockPresentPrice = backtestDataService.getStockPrice(code, date, 1)[0];
            return false;
        } catch (StockItemNotFoundException e) {
            return true;
        }
    }


    /**
     * 判断某股票在特定交易日是否跌停 公式：(C-C1)*100/C1 <= -(10-0.01*100/C1)
     * @param code
     * @param date
     * @return
     */
    private boolean isLimitDown(String code, LocalDate date){
        try {
            double[] stockLastTwoDayPrice = backtestDataService.getStockPrice(code, date, 2);
            double c1 = stockLastTwoDayPrice[0];
            double c = stockLastTwoDayPrice[1];

            // 判断
            if (isSTStock(code))
                return (c - c1) * 100 / c1 <= -(5 - 0.01 * 100 / c1);
            else
                return (c - c1) * 100 / c1 <= -(10 - 0.01 * 100 / c1);

        } catch (StockItemNotFoundException e) {
            return false;
        }

    }


    /**
     * 判断一只股票是否为ST股
     * @param code
     * @return
     */
    private boolean isSTStock(String code){
        String name = backtestDataService.getStockName(code);
        return name.contains("ST");
    }


    /**
     * 判断要交易的股票是否在持有股票里面
     * @param code
     * @return
     */
    private boolean isInStockHoldings(String code){
        return stockHoldings.get(code) != null;
    }



    /**
     * 判断该股票能不能进行买卖交易，如果停牌（即相应交易日没有数据），或涨跌停（注意ST股是特殊情况）
     * @param code
     * @param num
     * @param date
     * @return
     */
    private boolean canTrade(String code, int num, LocalDate date){
        // 若停牌则不能进行交易
        if (isSuspended(code, date))
            return false;

        // 判断是哪种交易类型
        TradeType tradeType = tradeAction(code, num);
        // 买入/卖出/持有
        if (tradeType == TradeType.Buy)
            return !isLimitUp(code, date);
        else if(tradeType == TradeType.Sell)
            return !isLimitDown(code, date);
        else
            return true;
    }

    /**
     * 判断order_to方法要进行的是哪种交易活动：-1卖出 0持有 1买入
     * @param code
     * @param num
     * @return
     */
    private TradeType tradeAction(String code, int num){
        int numInUniverse = getStockNumInStockHoldings(code);
        if (num > numInUniverse)
            return TradeType.Buy;
        else if(num < numInUniverse)
            return TradeType.Sell;
        else
            return TradeType.Hold;
    }


    /**
     * 更新持有股票列表
     * @param code
     * @param num
     */
    private void updateStockHoldings(String code, int num){
        // 若持有股票中没有该股票
        if (!isInStockHoldings(code)){
            // 股票数不为0才进行操作
            if (num != 0)
                stockHoldings.put(code, num);
        } else {
            if (num != 0)
                stockHoldings.put(code, num);
            else
                stockHoldings.remove(code);
        }
    }

    /**
     * 获得某只股票当前价格，若停牌，则为停牌前最后一次的价格
     * @param code
     * @param date
     * @return
     */
    private double getStockPresentPrice(String code, LocalDate date){

        try {
            return backtestDataService.getStockLastPriceFromMemory(code, date);
        } catch (StockItemNotFoundException e) {
            return 0;
        }
    }



    /**
     * 获得持有股票中某股票的数量
     * @param code
     * @return
     */
    private int getStockNumInStockHoldings(String code){
        return isInStockHoldings(code)? stockHoldings.get(code) : 0;
    }


    public double getCapitalPresent() {
        return capitalPresent;
    }

    public List<String> getUniverse() {
        return universe;
    }

    public void setUniverse(List<String> universe) {
        this.universe = universe;
    }

    public List<PositionRecordVO> getPositionRecords() {
        return positionRecords;
    }
}