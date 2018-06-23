package vo.backtest;

import java.time.LocalDate;

/**
 * Created by 97257 on 2017/4/2.
 * 用于给策略传给分析类的原始数据
 */
public class PriceVO {

    /**
     * 日期
     */
    private LocalDate date;

    /**
     * 账户的资产
     */
    private double account;

    /**
     * 基准的股票价格
     */
    private double benchmark;

    /**
     * 构造函数
     * @param date
     * @param account
     * @param benchmark
     */
    public PriceVO(LocalDate date, double account, double benchmark) {
        this.date = date;
        this.account = account;
        this.benchmark = benchmark;
    }

    public LocalDate getDate() {
        return date;
    }

    public double getAccount() {
        return account;
    }

    public double getBenchmark() {
        return benchmark;
    }
}
