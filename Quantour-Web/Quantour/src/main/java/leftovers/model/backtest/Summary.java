package leftovers.model.backtest;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;

/**
 * Created by Hiki on 2017/6/9.
 */
public class Summary {

    // 回测收益
    private double total_returns;

    // 回测年化收益
    private double annualized_returns;

    // 基准收益
    private double benchmark_total_returns;

    // 基准年化收益
    private double benchmark_annualized_returns;

    // Alpha
    private double alpha;

    // Beta
    private double beta;

    // Sharpe
    private double sharpe;

    // Sortino
    private double sortino;

    // Information Ratio
    private double information_ratio;

    // Volatility
    private double volatility;

    // 最大回测
    private double max_drawdown;

    // Tracking Error
    private double tracking_error;

    // 开始日期
    private String start_date;

    // 结束日期
    private String end_date;

    // 可用资金
    private double cash;

    // 起始资金
    private double stock_starting_cash;

    // 总权益
    private double total_value;

    // 实时净值
    private double unit_net_value;

    // 份额
    private double units;

    // downsize_risk
    private double downside_risk;

    public Summary() {
    }


    public Summary(double total_returns, double annualized_returns, double benchmark_total_returns, double benchmark_annualized_returns, double alpha, double beta, double sharpe, double sortino, double information_ratio, double volatility, double max_drawdown, double tracking_error, String start_date, String end_date, double cash, double stock_starting_cash, double total_value, double unit_net_value, double units, double downside_risk) {
        this.total_returns = total_returns;
        this.annualized_returns = annualized_returns;
        this.benchmark_total_returns = benchmark_total_returns;
        this.benchmark_annualized_returns = benchmark_annualized_returns;
        this.alpha = alpha;
        this.beta = beta;
        this.sharpe = sharpe;
        this.sortino = sortino;
        this.information_ratio = information_ratio;
        this.volatility = volatility;
        this.max_drawdown = max_drawdown;
        this.tracking_error = tracking_error;
        this.start_date = start_date;
        this.end_date = end_date;
        this.cash = cash;
        this.stock_starting_cash = stock_starting_cash;
        this.total_value = total_value;
        this.unit_net_value = unit_net_value;
        this.units = units;
        this.downside_risk = downside_risk;
    }

    public double getTotal_returns() {
        return total_returns;
    }

    public void setTotal_returns(double total_returns) {
        this.total_returns = total_returns;
    }

    public double getAnnualized_returns() {
        return annualized_returns;
    }

    public void setAnnualized_returns(double annualized_returns) {
        this.annualized_returns = annualized_returns;
    }

    public double getBenchmark_total_returns() {
        return benchmark_total_returns;
    }

    public void setBenchmark_total_returns(double benchmark_total_returns) {
        this.benchmark_total_returns = benchmark_total_returns;
    }

    public double getBenchmark_annualized_returns() {
        return benchmark_annualized_returns;
    }

    public void setBenchmark_annualized_returns(double benchmark_annualized_returns) {
        this.benchmark_annualized_returns = benchmark_annualized_returns;
    }

    public double getAlpha() {
        return alpha;
    }

    public void setAlpha(double alpha) {
        this.alpha = alpha;
    }

    public double getBeta() {
        return beta;
    }

    public void setBeta(double beta) {
        this.beta = beta;
    }

    public double getSharpe() {
        return sharpe;
    }

    public void setSharpe(double sharpe) {
        this.sharpe = sharpe;
    }

    public double getSortino() {
        return sortino;
    }

    public void setSortino(double sortino) {
        this.sortino = sortino;
    }

    public double getInformation_ratio() {
        return information_ratio;
    }

    public void setInformation_ratio(double information_ratio) {
        this.information_ratio = information_ratio;
    }

    public double getVolatility() {
        return volatility;
    }

    public void setVolatility(double volatility) {
        this.volatility = volatility;
    }

    public double getMax_drawdown() {
        return max_drawdown;
    }

    public void setMax_drawdown(double max_drawdown) {
        this.max_drawdown = max_drawdown;
    }

    public double getTracking_error() {
        return tracking_error;
    }

    public void setTracking_error(double tracking_error) {
        this.tracking_error = tracking_error;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public double getCash() {
        return cash;
    }

    public void setCash(double cash) {
        this.cash = cash;
    }

    public double getStock_starting_cash() {
        return stock_starting_cash;
    }

    public void setStock_starting_cash(double stock_starting_cash) {
        this.stock_starting_cash = stock_starting_cash;
    }

    public double getTotal_value() {
        return total_value;
    }

    public void setTotal_value(double total_value) {
        this.total_value = total_value;
    }

    public double getUnit_net_value() {
        return unit_net_value;
    }

    public void setUnit_net_value(double unit_net_value) {
        this.unit_net_value = unit_net_value;
    }

    public double getUnits() {
        return units;
    }

    public void setUnits(double units) {
        this.units = units;
    }

    public double getDownside_risk() {
        return downside_risk;
    }

    public void setDownside_risk(double downside_risk) {
        this.downside_risk = downside_risk;
    }
}
