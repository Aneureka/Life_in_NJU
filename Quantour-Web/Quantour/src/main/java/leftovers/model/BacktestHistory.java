package leftovers.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;

/**
 * Created by Hiki on 2017/6/12.
 */
@Entity
@Table(name = "backtest_history")
public class BacktestHistory {

    @Id
    @Column(name = "btHisId")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int btHisId;

    @Column(name = "algoId")
    private String algoId;

    @Column(name = "time")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private String time;

    @Column(name = "beginDate")
    private String beginDate;

    @Column(name = "endDate")
    private String endDate;

    @Column(name = "stockStartCash")
    private double stockStartCash;

    @Column(name = "code", columnDefinition = "TEXT")
    private String code;

    @Column(name = "total_returns")
    private double totalReturns;

    @Column(name = "annualized_returns")
    private double annualizedReturns;

    @Column(name = "benchmark_total_returns")
    private double benchmarkTotalReturns;

    @Column(name = "alpha")
    private double alpha;

    @Column(name = "sharpe")
    private double sharpe;

    @Column(name = "max_drawdown")
    private double maxDrawdown;

    @Column(name = "status")
    private int status;


    public BacktestHistory() {
    }

    public BacktestHistory(String algoId, String time, String beginDate, String endDate, double stockStartCash, String code, double totalReturns, double annualizedReturns, double benchmarkTotalReturns, double alpha, double sharpe, double maxDrawdown, int status) {
        this.algoId = algoId;
        this.time = time;
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.stockStartCash = stockStartCash;
        this.code = code;
        this.totalReturns = totalReturns;
        this.annualizedReturns = annualizedReturns;
        this.benchmarkTotalReturns = benchmarkTotalReturns;
        this.alpha = alpha;
        this.sharpe = sharpe;
        this.maxDrawdown = maxDrawdown;
        this.status = status;
    }

    public int getBtHisId() {
        return btHisId;
    }

    public void setBtHisId(int btHisId) {
        this.btHisId = btHisId;
    }

    public String getAlgoId() {
        return algoId;
    }

    public void setAlgoId(String algoId) {
        this.algoId = algoId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public double getStockStartCash() {
        return stockStartCash;
    }

    public void setStockStartCash(double stockStartCash) {
        this.stockStartCash = stockStartCash;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public double getTotalReturns() {
        return totalReturns;
    }

    public void setTotalReturns(double totalReturns) {
        this.totalReturns = totalReturns;
    }

    public double getAnnualizedReturns() {
        return annualizedReturns;
    }

    public void setAnnualizedReturns(double annualizedReturns) {
        this.annualizedReturns = annualizedReturns;
    }

    public double getBenchmarkTotalReturns() {
        return benchmarkTotalReturns;
    }

    public void setBenchmarkTotalReturns(double benchmarkTotalReturns) {
        this.benchmarkTotalReturns = benchmarkTotalReturns;
    }

    public double getAlpha() {
        return alpha;
    }

    public void setAlpha(double alpha) {
        this.alpha = alpha;
    }

    public double getSharpe() {
        return sharpe;
    }

    public void setSharpe(double sharpe) {
        this.sharpe = sharpe;
    }

    public double getMaxDrawdown() {
        return maxDrawdown;
    }

    public void setMaxDrawdown(double maxDrawdown) {
        this.maxDrawdown = maxDrawdown;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
