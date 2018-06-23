package bl.backtest.strategy;

import bl.backtest.account.Account;
import com.sun.org.apache.bcel.internal.generic.LoadClass;
import dao.backtest.BackTestDao;
import dataservice.backtestdata.BacktestDataService;
import util.enums.Benchmark;
import util.exception.StockItemNotFoundException;
import util.exception.StockNotFoundException;
import vo.backtest.PriceVO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by 97257 on 2017/4/2.
 * 所有策略的父类，抽象类
 */
public abstract class Strategy {

    /**
     * 回测开始时间
     */
    protected LocalDate beginTime;



    /**

     * 回测结束时间
     */
    protected LocalDate endTime;

    /**
     * 基准数据
     */
    protected Map<LocalDate, Double> benchmarkData;

    /**
     * 回测的数据层对象
     */
    protected BacktestDataService backtestDs;

    /**
     * 账户
     */
    protected Account account;

    /**
     * 回测结果原始数据，用于给分析模块初始化
     */
    protected List<PriceVO> prices;

    /**
     * 持有期
     */
    protected int refreshRate;


    /**
     * 形成期
     */
    protected int formRate;

    /**
     * 构造函数
     */
    public Strategy(LocalDate beginTime, LocalDate endTime, Map<LocalDate, Double> benchmarkData, Account account, int refreshRate, int formRate) throws StockNotFoundException {
        init(beginTime, endTime, benchmarkData, account, refreshRate, formRate);
    }

    /**
     * 初始化Strategy
     */
    private void init(LocalDate beginTime, LocalDate endTime, Map<LocalDate, Double> benchmarkData, Account account, int refreshRate, int formRate) throws StockNotFoundException {
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.benchmarkData = benchmarkData;
        this.backtestDs = new BackTestDao();
        this.account = account;
        this.prices = new ArrayList<>();
        this.refreshRate = refreshRate;
        this.formRate = formRate;

    }

    /**
     * 策略的核心部分，抽象方法，由子类实现
     */
    public abstract void handleData(LocalDate day);

    /**
     * 执行回测
     */
    public void execute(){

        // 获取用户输入的时间段之间的交易日数据
        List<LocalDate> tradeDay = backtestDs.getTradeDays(this.beginTime, this.endTime);

        // for循环,每到refreshRate执行一次handleData
        for (LocalDate day : tradeDay){

            // 如果是handleDay，执行handle方法
            if (isHandleDay(tradeDay, refreshRate, day)){
                handleData(day);
            }


            // 获得account的总资产
            double money = account.getCapitalAll(day);

            // 获得基准的指数
            double benchmarkIndex = benchmarkData.get(day);

            // 增加当天的价格记录
            prices.add(new PriceVO(day, money, benchmarkIndex));
        }

    }


    protected void order(List<String> buylist, LocalDate day, Map<String, Double[]> stockPrices){


        // 将account中非赢家组合的股票卖掉
        for (String stock : account.getStockHoldingList()){
            if (!buylist.contains(stock))
                account.order_to(stock, 0, day);
        }

        // 可供每个股票买的资金
        double availableMoney = account.getCapitalAvailable(day) / buylist.size();

        // 等权重买入所选股票
        for (String stock : buylist){
            // 买入
            int num = (int) (availableMoney / stockPrices.get(stock)[0]);
            account.order_to(stock, num, day);
        }

        // 买完之后更新持仓记录
        account.appendPositionRecord(day);

    }


    /**
     * 判断是否为执行handle的交易日
     */
    private boolean isHandleDay(List<LocalDate> tradeDay, int refreshRate, LocalDate day){

        // 获得索引
        int index = tradeDay.indexOf(day);

        // 如果为0，则为执行handle的交易日
        return index % refreshRate == 0;

    }







/*----------------------------------------大概毫无卵用的方法-----------------------------------------------*/

    public LocalDate getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(LocalDate beginTime) {
        this.beginTime = beginTime;
    }

    public LocalDate getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDate endTime) {
        this.endTime = endTime;
    }

    public List<PriceVO> getPrices() {
        return prices;
    }

    public int getFormRate() {
        return formRate;
    }

    public int getRefreshRate() {
        return refreshRate;
    }

    public void setRefreshRate(int refreshRate) {
        this.refreshRate = refreshRate;
    }

    public void setFormRate(int formRate) {
        this.formRate = formRate;
    }
}