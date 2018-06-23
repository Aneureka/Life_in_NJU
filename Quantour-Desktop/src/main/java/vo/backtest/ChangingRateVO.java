package vo.backtest;

import util.tool.NumberFormatter;

import java.time.LocalDate;

/**
 * Created by Hiki on 2017/4/16.
 */
public class ChangingRateVO {

    /**
     * 相对强弱计算周期（持有期、形成期）
     */
    private int rate;


    /**
     * 超额收益率
     */
    private double abnormalReturn;


    /**
     * 一年内胜率
     */
    private double winingPercentage;


    public ChangingRateVO(int rate, double abnormalReturn, double winingPercentage) {
        this.rate = rate;
        this.abnormalReturn = abnormalReturn;
        this.winingPercentage = winingPercentage;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public double getAbnormalReturn() {
        return abnormalReturn;
    }

    public void setAbnormalReturn(double abnormalReturn) {
        this.abnormalReturn = abnormalReturn;
    }

    public double getWiningPercentage() {
        return winingPercentage;
    }

    public void setWiningPercentage(double winingPercentage) {
        this.winingPercentage = winingPercentage;
    }

    public String getReturnStr() {
        return NumberFormatter.format(abnormalReturn) + "%";
    }

    public String getWinPerString() {
        return NumberFormatter.formatToPercent(winingPercentage) + "%";
    }
}
