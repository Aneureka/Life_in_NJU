package vo.backtest;

/**
 * Created by kevin on 2017/4/16.
 */
public class BacktestResultOverviewVO {

    /**
     * 夏普比率
     */
    public double sharpRatio;

    /**
     * 收益波动率
     */
    public double volatility;

    /**
     * 策略年化收益
     */
    public double strategyAnnualisedReturn;

    /**
     * 基准年化收益
     */
    public double benchmarkAnnualisedReturn;

    /**
     * alpha
     */
    public double alpha;

    /**
     * beta
     */
    public double beta;

    /**
     * 最大回撤
     */
    public double maxDrawdown;

    /**
     * 总收益
     */
    public double totalReturn;

    /**
     * 累计收益（策略和基准）
     */
    public DailyCumReturnVO dailyCumReturn;

    public BacktestResultOverviewVO(double sharpRatio, double volatility, double strategyAnnualisedReturn, double benchmarkAnnualisedReturn, double alpha, double beta, double maxDrawdown, double totalReturn, DailyCumReturnVO dailyCumReturn) {
        this.sharpRatio = sharpRatio;
        this.volatility = volatility;
        this.strategyAnnualisedReturn = strategyAnnualisedReturn;
        this.benchmarkAnnualisedReturn = benchmarkAnnualisedReturn;
        this.alpha = alpha;
        this.beta = beta;
        this.maxDrawdown = maxDrawdown;
        this.totalReturn = totalReturn;
        this.dailyCumReturn = dailyCumReturn;
    }

}
