package leftovers.service.backtestserviceimpl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import leftovers.model.backtest.*;
import leftovers.util.DataFrame;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Hiki on 2017/6/8.
 */
@Component
public class Analyser {

    private Summary summary;

    private List<Portfolio> portfolios;

    private List<BenchmarkPortfolio> benchmarkPortfolios;

    private List<StockAccount> stockAccounts;

    private List<StockPosition> stockPositions;

    private List<Trade> trades;

    private Map<LocalDate, List<TradeRecord>> tradeRecords;

    private Map<LocalDate, List<DailyPosition>> dailyPositions;

    private Analysis analysis;

    public Analyser() {
        tradeRecords = new TreeMap<>(LocalDate::compareTo);
        dailyPositions = new TreeMap<>(LocalDate::compareTo);
    }

    private void init(Map<String, String> rawResults){
        this.summary = JSON.parseObject(rawResults.get("summary"), Summary.class);
        this.portfolios = JSON.parseArray(rawResults.get("portfolio"), Portfolio.class);
        this.benchmarkPortfolios = JSON.parseArray(rawResults.get("benchmark_portfolio"), BenchmarkPortfolio.class);
        this.stockAccounts = JSON.parseArray(rawResults.get("stock_account"), StockAccount.class);
        this.stockPositions = JSON.parseArray(rawResults.get("stock_positions"), StockPosition.class);
        this.trades = JSON.parseArray(rawResults.get("trades"), Trade.class);
        generateTradeRecords();
        generateDailyPositions();
        analysis = new Analysis(portfolios, benchmarkPortfolios);
    }

    public String analyse(Map<String, String> rawResults){
        init(rawResults);
        return getResultJson();
    }



    // 生成收益概览
    public org.json.JSONObject produceSummary(){

        // 时间
        List<String> date_times = portfolios.stream()
                .map(e -> LocalDateTime.ofInstant(Instant.ofEpochMilli(Long.parseLong(e.getDate())), ZoneId.systemDefault()).toString())
                .collect(Collectors.toList());

        // 策略累计收益率
        List<Double> total_returns = portfolios.stream().mapToDouble(e -> e.getUnit_net_value()-1).boxed().collect(Collectors.toList());

        // 基准累计收益率
        List<Double> benchmark_total_returns = benchmarkPortfolios.stream().mapToDouble(e -> e.getUnit_net_value()-1).boxed().collect(Collectors.toList());

        // 当日盈亏
        List<Double> daily_pnls = portfolios.stream().mapToDouble(e -> e.getTotal_value() - e.getStatic_unit_net_value() * e.getUnits()).boxed().collect(Collectors.toList());

        // 买卖日期
        List<LocalDate> trade_dates = new ArrayList<>(new HashSet<LocalDate>(trades.stream().map(e -> e.getDatetime().toLocalDate()).collect(Collectors.toList())));
        trade_dates.sort(LocalDate::compareTo);

        // 当日买入
        List<Double> buys = new ArrayList<>();
        List<Double> sells = new ArrayList<>();

        for (LocalDate date : trade_dates){
            List<TradeRecord> todayTradeRecords = tradeRecords.get(date);
            buys.add(todayTradeRecords.stream()
                    .filter(e -> e.getSide().equals("BUY"))
                    .mapToDouble(e -> e.getLast_quantity() * e.getLast_price())
                    .sum());
            sells.add(todayTradeRecords.stream()
                    .filter(e -> e.getSide().equals("SELL"))
                    .mapToDouble(e -> e.getLast_quantity() * e.getLast_price())
                    .sum());
        }

        org.json.JSONObject summaryJson = new org.json.JSONObject()
                .put("total_returns", summary.getTotal_returns())
                .put("annualized_returns", summary.getAnnualized_returns())
                .put("benchmark_total_returns", summary.getBenchmark_total_returns())
                .put("benchmark_annualized_returns", summary.getBenchmark_annualized_returns())
                .put("alpha", summary.getAlpha())
                .put("beta", summary.getBeta())
                .put("sharpe", summary.getSharpe())
                .put("sortino", summary.getSortino())
                .put("information_ratio", summary.getInformation_ratio())
                .put("volatility", summary.getVolatility())
                .put("max_drawdown", summary.getMax_drawdown())
                .put("tracking_error", summary.getTracking_error())
                .put("downside_risk", summary.getDownside_risk())
                .put("start_date", summary.getStart_date())
                .put("end_date", summary.getEnd_date())
                .put("date_times", date_times)
                .put("daily_total_returns", total_returns)
                .put("benchmark_daily_total_returns", benchmark_total_returns)
                .put("daily_pnls", daily_pnls)
                .put("trade_dates", trade_dates)
                .put("buys", buys)
                .put("sells", sells);

        return summaryJson;
    }

    // 生成交易详情
    public org.json.JSONArray produceTradeDetails(){

        org.json.JSONArray tradeDetailsJson = new org.json.JSONArray();

        Iterator<Map.Entry<LocalDate, List<TradeRecord>>> itr = tradeRecords.entrySet().iterator();
        while (itr.hasNext()) {
            Map.Entry<LocalDate, List<TradeRecord>> next = itr.next();
            LocalDate date = next.getKey();
            List<TradeRecord> tradeRecords = next.getValue();
            org.json.JSONObject tradeDetailJson = new org.json.JSONObject()
                    .put("date", date)
                    .put("trade_num", tradeRecords.size())
                    .put("trade_records", tradeRecords);
            tradeDetailsJson.put(tradeDetailJson);
        }

        return tradeDetailsJson;

    }

    // 生成每日持仓
    public org.json.JSONArray produceStockPosition(){

        org.json.JSONArray positionJson = new org.json.JSONArray();

        Iterator<Map.Entry<LocalDate, List<DailyPosition>>> itr = dailyPositions.entrySet().iterator();

        while (itr.hasNext()) {
            Map.Entry<LocalDate, List<DailyPosition>> next = itr.next();
            LocalDate date = next.getKey();
            List<DailyPosition> dailyPosition = next.getValue();
            org.json.JSONObject dailyPositionJson = new org.json.JSONObject()
                    .put("date", date)
                    .put("daily_positions", dailyPosition);
            positionJson.put(dailyPositionJson);
        }

        return positionJson;

    }

    // 生成账户信息
    public org.json.JSONArray produceStockAccount(){

        org.json.JSONArray jarr = new org.json.JSONArray();
        for (StockAccount each : stockAccounts) {
            org.json.JSONObject jobj = new org.json.JSONObject()
                    .put("date", LocalDateTime.ofInstant(Instant.ofEpochMilli(Long.parseLong(each.getDate())), ZoneId.systemDefault()).toLocalDate().toString())
                    .put("transaction_cost", each.getTransaction_cost())
                    .put("total_value", each.getTotal_value())
                    .put("market_value", each.getMarket_value())
                    .put("cash", each.getCash())
                    .put("total_pnls", each.getTotal_value() - summary.getStock_starting_cash());
            jarr.put(jobj);
        }

        return jarr;
    }

    // 生成收益
    public String produceReturns(){
        DataFrame strategyReturns = analysis.getReturns(Analysis.STRATEGY);
        DataFrame benchmarkReturns = analysis.getReturns(Analysis.BENCHMARK);

        JSONObject jsonObject = new JSONObject(){
            {
                put("strategyReturns", JSON.parse(strategyReturns.toJSON()));
                put("benchmarkReturns", JSON.parse(benchmarkReturns.toJSON()));
            }
        };

        return jsonObject.toString();
    }

    // 生成夏普比率
    public String produceSharpRatios(){
        return analysis.getSharpRatios(Analysis.STRATEGY).toJSON();
    }

    // 生成alpha
    public String produceAlphas(){
        return analysis.getAlphas().toJSON();
    }

    // 生成beta
    public String produceBetas(){
        return analysis.getBetas().toJSON();
    }

    // 生成最大回撤
    public String produceMaxDrawdowns(){
        return analysis.getMaxDrawdowns(Analysis.STRATEGY).toJSON();
    }

    // 生成收益波动率
    public String produceVolatilitys() {
        DataFrame strategyVolatilitys = analysis.getVolatilitys(Analysis.STRATEGY);
        DataFrame benchmarkVolatilitys = analysis.getVolatilitys(Analysis.BENCHMARK);

        JSONObject jsonObject = new JSONObject() {
            {
                put("strategyReturns", JSON.parse(strategyVolatilitys.toJSON()));
                put("benchmarkReturns", JSON.parse(benchmarkVolatilitys.toJSON()));
            }
        };

        return jsonObject.toString();
    }

    // 生成信息比率
    public String produceInformationRatios(){
        return analysis.getInformationRatios().toJSON();
    }


    // 生成交易记录
    private void generateTradeRecords(){

        if (trades.isEmpty()){
            return;
        }

        LocalDate today = trades.get(0).getDatetime().toLocalDate();

        List<TradeRecord> todayTrades = new ArrayList<>();

        // 为每个交易日期
        for(int i = 0; i < trades.size(); i++) {
            if (trades.get(i).getDatetime().toLocalDate().isEqual(today) ){
                todayTrades.add(new TradeRecord(trades.get(i).getTrading_datetime(), trades.get(i).getOrder_book_id(), trades.get(i).getSymbol(), trades.get(i).getSide(), trades.get(i).getLast_quantity(), trades.get(i).getLast_price(), trades.get(i).getTransaction_cost()));
            } else {
                // 加入交易记录
                tradeRecords.put(today, new ArrayList<>(todayTrades));
                // 更新临时记录
                today = trades.get(i).getDatetime().toLocalDate();
                todayTrades.clear();
                todayTrades.add(new TradeRecord(trades.get(i).getTrading_datetime(), trades.get(i).getOrder_book_id(), trades.get(i).getSymbol(), trades.get(i).getSide(), trades.get(i).getLast_quantity(), trades.get(i).getLast_price(), trades.get(i).getTransaction_cost()));
            }

            if (i == trades.size() - 1){
                tradeRecords.put(today, new ArrayList<>(todayTrades));
            }

        }

    }

    // 生成每日持仓
    private void generateDailyPositions(){

        if (stockPositions.isEmpty()){
            return;
        }

        LocalDateTime today = LocalDateTime.ofInstant(Instant.ofEpochMilli(Long.parseLong(stockPositions.get(0).getDate())), ZoneId.systemDefault());

        List<DailyPosition> todayPosition = new ArrayList<>();

        // 为每个持仓日期
        for(int i = 0; i < stockPositions.size(); i++) {

            LocalDateTime time = LocalDateTime.ofInstant(Instant.ofEpochMilli(Long.parseLong(stockPositions.get(i).getDate())), ZoneId.systemDefault());

            if (time.toLocalDate().isEqual(today.toLocalDate())){
                todayPosition.add(new DailyPosition(time, stockPositions.get(i).getOrder_book_id(), stockPositions.get(i).getSymbol(), stockPositions.get(i).getLast_price(), stockPositions.get(i).getQuantity(), stockPositions.get(i).getAvg_price(), stockPositions.get(i).getMarket_value()));
            } else {
                // 加入交易记录
                dailyPositions.put(today.toLocalDate(), new ArrayList<>(todayPosition));
                // 更新临时记录
                today = LocalDateTime.ofInstant(Instant.ofEpochMilli(Long.parseLong(stockPositions.get(i).getDate())), ZoneId.systemDefault());
                todayPosition.clear();
                todayPosition.add(new DailyPosition(time, stockPositions.get(i).getOrder_book_id(), stockPositions.get(i).getSymbol(), stockPositions.get(i).getLast_price(), stockPositions.get(i).getQuantity(), stockPositions.get(i).getAvg_price(), stockPositions.get(i).getMarket_value()));
            }

            if (i == stockPositions.size() - 1){
                dailyPositions.put(today.toLocalDate(), new ArrayList<>(todayPosition));
            }

        }

    }

    public Map<LocalDate, List<TradeRecord>> getTradeRecords() {
        return tradeRecords;
    }

    public void setTradeRecords(Map<LocalDate, List<TradeRecord>> tradeRecords) {
        this.tradeRecords = tradeRecords;
    }

    private String getResultJson(){
        JSONObject jsonObject = new JSONObject(){
            {
                put("retCode", 1);
                put("summary", JSON.parse(produceSummary().toString()));
                put("stock_account", JSON.parse(produceStockAccount().toString()));
                put("stock_position", JSON.parse(produceStockPosition().toString()));
                put("trade_details", JSON.parse(produceTradeDetails().toString()));
                put("returns", JSON.parse(produceReturns()));
                put("alphas", JSON.parse(produceAlphas()));
                put("betas", JSON.parse(produceBetas()));
                put("max_drawdowns", JSON.parse(produceMaxDrawdowns()));
                put("volatilitys", JSON.parse(produceVolatilitys()));
                put("sharp_ratios", JSON.parse(produceSharpRatios()));
                put("information_ratios", JSON.parse(produceInformationRatios()));
            }
        };
        return jsonObject.toString();
    }

    public Summary getSummary() {
        return summary;
    }

    public void setSummary(Summary summary) {
        this.summary = summary;
    }

    public List<Portfolio> getPortfolios() {
        return portfolios;
    }

    public void setPortfolios(List<Portfolio> portfolios) {
        this.portfolios = portfolios;
    }

    public List<BenchmarkPortfolio> getBenchmarkPortfolios() {
        return benchmarkPortfolios;
    }

    public void setBenchmarkPortfolios(List<BenchmarkPortfolio> benchmarkPortfolios) {
        this.benchmarkPortfolios = benchmarkPortfolios;
    }

    public List<StockAccount> getStockAccounts() {
        return stockAccounts;
    }

    public void setStockAccounts(List<StockAccount> stockAccounts) {
        this.stockAccounts = stockAccounts;
    }

    public List<StockPosition> getStockPositions() {
        return stockPositions;
    }

    public void setStockPositions(List<StockPosition> stockPositions) {
        this.stockPositions = stockPositions;
    }

    public List<Trade> getTrades() {
        return trades;
    }

    public void setTrades(List<Trade> trades) {
        this.trades = trades;
    }

    public Map<LocalDate, List<DailyPosition>> getDailyPositions() {
        return dailyPositions;
    }

    public void setDailyPositions(Map<LocalDate, List<DailyPosition>> dailyPositions) {
        this.dailyPositions = dailyPositions;
    }

    public Analysis getAnalysis() {
        return analysis;
    }

    public void setAnalysis(Analysis analysis) {
        this.analysis = analysis;
    }
}














//    public String analyse(Map<String, String> rawResults){
//        init(rawResults);
//        Iterator<Map.Entry<String, String>> iterator = rawResults.entrySet().iterator();
//        while (iterator.hasNext()){
//            Map.Entry<String, String> next = iterator.next();
//            System.out.println(next.getKey() + ": " + next.getValue());
//        }
//        return "";
//    }