package bl.stock;

import blservice.stockblservice.StockBLService;
import dao.backtest.BackTestDao;
import dao.stock.StockDao;
import dataservice.backtestdata.BacktestDataService;
import dataservice.stock.StockDataService;
import util.exception.StockNotFoundException;
import util.exception.TimeException;
import po.stock.StockItemPO;
import util.tool.StockCommonInfo;
import vo.stock.StockItemVO;
import vo.stock.StockVO;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by kevin on 2017/3/6.
 */
public class Stock implements StockBLService {

    StockItem stockItem;
    StockDataService stockDataService;
    BacktestDataService backtestDataService;

    public Stock() {
        this.stockItem = new StockItem();
        this.stockDataService = new StockDao();
        this.backtestDataService = new BackTestDao();
    }

    @Override
    public StockVO findStockByName(String name) throws StockNotFoundException, TimeException {
        String code = stockDataService.findCodeByName(name);
        return findStockByCode(code);
    }

    @Override
    public StockVO findStockByCode(String code) throws TimeException, StockNotFoundException {
        LocalDate endTime = getLastTradeday(code);
        LocalDate beginTime = endTime.minusMonths(1);
        return findStockByCode(code, beginTime, endTime);
    }

    @Override
    public StockVO findStockByName(String name, LocalDate beginTime, LocalDate endTime) throws StockNotFoundException, TimeException {
        String code = stockDataService.findCodeByName(name);
        return findStockByCode(code, beginTime, endTime);

    }

    @Override
    public StockVO findStockByCode(String code, LocalDate beginTime, LocalDate endTime) throws StockNotFoundException, TimeException {

        // 先判断时间是否输入正确
        if (beginTime.isAfter(endTime)) {
            throw new TimeException("The beginTime is after endTime!");
        }

        // 先把这只股票的所有股票项目取出来
        List<StockItemPO> stockItemPOs = stockDataService.findStockPOByCode(code).getStockItemList();

        // 将每个StockItemPO转换称StockItemVO
        List<StockItemVO> stockItemVOs = stockItemPOs.stream().map(sp -> stockItem.toStockItemVO(sp)).collect(Collectors.toList());
        // 计算每个StockItemVO的涨跌额，涨跌幅，对数收益率，多个均值
        setChangeAndIncrease(stockItemVOs);
        setLogReturn(stockItemVOs);
        setAverages(stockItemVOs);
        stockItemVOs = getRequired(stockItemVOs, beginTime, endTime);

        if(stockItemVOs.isEmpty()){
            throw new StockNotFoundException("There exists no stocks between " + beginTime.toString() + " and " + endTime.toString());
        }


        // 把最后一个StockItemVO部分信息提取作为整支股票的部分宏观数据
        StockItemVO theLastItem = stockItemVOs.get(stockItemVOs.size() - 1);
        double presentPrice = theLastItem.close;
        double change = theLastItem.change;
        double increase = theLastItem.increase;
        double logReturnVariance = getLogReturnVariance(stockItemVOs);

        return new StockVO(stockItemVOs, presentPrice, change, increase, logReturnVariance);

    }

    @Override
    public StockVO findMonthStockByCode(String code, LocalDate beginTime, LocalDate endTime) throws TimeException, StockNotFoundException {
        // 先获得总体的信息，接下来只要以每一个月为界限分组
        LocalDate firstDay = beginTime.with(TemporalAdjusters.firstDayOfMonth());
        LocalDate lastDay = endTime.with(TemporalAdjusters.lastDayOfMonth());
        StockVO stockVO = findStockByCode(code, firstDay, lastDay);

        List<StockItemVO> stockItemVOs = stockVO.getStockItemList();
        List<StockItemVO> monthStockItemVOs = new ArrayList<>();

        for (LocalDate date = firstDay; !date.isAfter(lastDay); date = date.with(TemporalAdjusters.firstDayOfNextMonth())){
            LocalDate finalDate = date;
            List<StockItemVO> stockItemToPutIn = stockItemVOs.stream()
                                                    .filter(e -> !e.date.isBefore(finalDate) && !e.date.isAfter(finalDate.with(TemporalAdjusters.lastDayOfMonth())))
                                                    .collect(Collectors.toList());
            monthStockItemVOs.add(createNewStockItem(stockItemToPutIn));
        }

        stockVO.stockItemList = monthStockItemVOs;
        return stockVO;
    }

    @Override
    public StockVO findMonthStockByName(String name, LocalDate beginTime, LocalDate endTime) throws TimeException, StockNotFoundException{
        String code = stockDataService.findCodeByName(name);
        return findMonthStockByCode(code, beginTime, endTime);
    }

    @Override
    public StockVO findWeekStockByCode(String code, LocalDate beginTime, LocalDate endTime) throws TimeException, StockNotFoundException {
        // 先获得总体的信息，接下来只要以每一周为界限分组
        LocalDate firstDay = beginTime.minusDays(beginTime.getDayOfWeek().getValue()-1);
        LocalDate lastDay = endTime.plusDays(7-beginTime.getDayOfWeek().getValue());
        StockVO stockVO = findStockByCode(code, firstDay, lastDay);

        List<StockItemVO> stockItemVOs = stockVO.getStockItemList();
        List<StockItemVO> monthStockItemVOs = new ArrayList<>();

        for (LocalDate date = firstDay; !date.isAfter(lastDay.minusDays(7)); date = date.plusDays(7)){
            LocalDate finalDate = date;
            List<StockItemVO> stockItemToPutIn = stockItemVOs.stream()
                    .filter(e -> !e.date.isBefore(finalDate) && !e.date.isAfter(finalDate.plusDays(7)))
                    .collect(Collectors.toList());
            monthStockItemVOs.add(createNewStockItem(stockItemToPutIn));
        }

        stockVO.stockItemList = monthStockItemVOs;
        return stockVO;

    }

    @Override
    public StockVO findWeekStockByName(String name, LocalDate beginTime, LocalDate endTime) throws TimeException, StockNotFoundException{
        String code = stockDataService.findCodeByName(name);
        return findWeekStockByCode(code, beginTime, endTime);
    }

    /**
     * 通过查询获得建议的股票代码和名称列表，其中代码优先级高
     *
     * @param query
     * @return
     * @throws StockNotFoundException
     */
    @Override
    public List<String> findSuggestions(String query) throws StockNotFoundException {
        // 获取股票code-name映射关系
        Map<String, String> code_name = stockDataService.findCodeName();
        // 分别取出code、name列表
        List<String> codeSug = code_name.keySet().stream().filter(c -> c.contains(query)).collect(Collectors.toList());
        List<String> nameSug = code_name.values().stream().filter(s -> s.contains(query)).collect(Collectors.toList());
        codeSug.addAll(nameSug);
        codeSug.sort(Comparator.comparing(String::toString));

        if (codeSug.isEmpty()){
            throw new StockNotFoundException("No suggestion found by query: " + query);
        }

        return codeSug;

    }

    @Override
    public List<String> getSections() {
        return backtestDataService.getSections();
    }

    @Override
    public List<String> getStockCodesBySection(String sectionName) {
        return backtestDataService.getStockCodesBySection(sectionName);
    }

    @Override
    public String getSectionByStockCode(String code) {
        return backtestDataService.getSectionByStockCode(code);
    }

    /**
     * 过滤掉不需要的日期
     * @param stockItemVOs
     * @param beginTime
     * @param endTime
     * @return
     */
    private List<StockItemVO> getRequired(List<StockItemVO> stockItemVOs, LocalDate beginTime, LocalDate endTime) {

        return stockItemVOs.stream().filter(s -> (!s.date.isBefore(beginTime)) && (!s.date.isAfter(endTime))).collect(Collectors.toList());

    }


    /**
     * 设置每天的涨跌额，涨跌幅
     * @param stockItemVOs
     */
    private void setChangeAndIncrease(List<StockItemVO> stockItemVOs){
        // 用今天的复权收盘价与前一天的复权收盘价比较，并进行设置
        for(int i = 1; i < stockItemVOs.size(); i++){
            StockItemVO stockItemVO = stockItemVOs.get(i);
            stockItemVO.change = stockItemVOs.get(i).adjClose - stockItemVOs.get(i-1).adjClose;
            stockItemVO.increase = (stockItemVOs.get(i).adjClose - stockItemVOs.get(i-1).adjClose) / stockItemVOs.get(i).adjClose;
            stockItemVOs.set(i, stockItemVO);
        }
    }


    /**
     * 设置每天的对数收益率
     * @param stockItemVOs
     */
    public void setLogReturn(List<StockItemVO> stockItemVOs){
        for(int i = 1; i < stockItemVOs.size(); i++){
            StockItemVO stockItemVO = stockItemVOs.get(i);
            stockItemVO.logReturn = Math.log(stockItemVOs.get(i).adjClose / stockItemVOs.get(i-1).adjClose);
            stockItemVOs.set(i, stockItemVO);
        }
    }

    /**
     * 设置每天的均值
     * @param stockItemVOs
     */
    private void setAverages(List<StockItemVO> stockItemVOs){
        for (int days : StockCommonInfo.AVERAGE_DAY) {
            setEachAverage(stockItemVOs, days);
        }

    }

    /**
     * 设置均值
     * @param stockItemVOs
     * @param n
     */
    private void setEachAverage(List<StockItemVO> stockItemVOs, int n) {
        for(int i = n-1; i < stockItemVOs.size(); i++){
            StockItemVO stockItemVO = stockItemVOs.get(i);
            double averageN = 0.0;
            for (int j = i-n+1; j <= i; j++)
                averageN += stockItemVOs.get(j).close / n;
            stockItemVO.averages.put(n, averageN);
            stockItemVOs.set(i, stockItemVO);
        }

    }


    /**
     * 获取对数收益率方差
     * @param stockItemVOs
     * @return
     */
    public double getLogReturnVariance(List<StockItemVO> stockItemVOs){

        // 将StockItemVO中的LogReturn提取出来
        List<Double> logReturns = new ArrayList<>();
        for(int i = 1; i < stockItemVOs.size(); i++) {
            logReturns.add(stockItemVOs.get(i).logReturn);
        }
        return getVariance(logReturns);
    }

    /**
     * 计算方差
     */
    private double getVariance(List<Double> nums){

        // 先计算平均值
        double average = getAverage(nums);

        // 计算偏差平方的和
        double d2 = nums.stream().mapToDouble(num -> (num - average) * (num - average)).sum();

        return d2 / nums.size();
    }


    /**
     * 计算平均值
     */
    private double getAverage(List<Double> nums){
        return nums.stream().mapToDouble(num -> num).average().getAsDouble();
    }


    /**
     * 获取最后一个交易日
     * @param code
     * @return
     */
    private LocalDate getLastTradeday(String code) throws TimeException, StockNotFoundException {
        List<StockItemPO> stockItems = stockDataService.findStockPOByCode(code).getStockItemList();
        return stockItems.get(stockItems.size()-1).getDate();
    }


    private StockItemVO createNewStockItem(List<StockItemVO> stockItemVOs){

        if (stockItemVOs.isEmpty())
            return new StockItemVO();

        StockItemVO first = stockItemVOs.get(0);
        StockItemVO last = stockItemVOs.get(stockItemVOs.size()-1);

        LocalDate date = first.date.minusDays(first.date.getDayOfWeek().getValue()-1);

        double open = first.open;
        double close = last.close;
        double adjClose = last.adjClose;
        double volume = last.volume;
        double high = stockItemVOs.stream().mapToDouble(e -> e.high).max().orElse(0);
        double low = stockItemVOs.stream().mapToDouble(e -> e.low).min().orElse(0);
        String code = first.code;
        String name = first.name;
        String market = first.market;
        double change = last.change;
        double increase = last.increase;
        double logReturn = last.logReturn;
        Map<Integer, Double> averages = last.averages;

        return new StockItemVO(date, open, high, low, close, volume, adjClose, code, name, market, change, increase, logReturn, averages);
    }


}
