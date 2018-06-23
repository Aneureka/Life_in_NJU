package vo.backtest;

/**
 * Created by kevin on 2017/4/16.
 */
public class WinRateVO {

    /**
     * 累计超额收益率
     */
    public double CAR;

    /**
     * 胜率
     */
    public double winRate;

    public WinRateVO(double CAR, double winRate) {
        this.CAR = CAR;
        this.winRate = winRate;
    }

}
