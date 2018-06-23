package vo.backtest;

/**
 * Created by kevin on 2017/4/16.
 */
public class ReturnDistributionVO {

    /**
     * 正收益周期个数
     */
    public int positiveReturnNum;

    /**
     * 负收益周期个数
     */
    public int negativeReturnNum;

    /**
     * 赢率
     */
    public double winRate;

    /**
     * 分割点
     */
    public double[] cutPoints;

    /**
     * 正收益分布
     */
    public int[] positiveDistribution;

    /**
     * 负收益分布
     */
    public int[] negativeDistribution;

    public ReturnDistributionVO(int positiveReturnNum, int negativeReturnNum, double winRate, double[] cutPoints, int[] positiveDistribution, int[] negativeDistribution) {
        this.positiveReturnNum = positiveReturnNum;
        this.negativeReturnNum = negativeReturnNum;
        this.winRate = winRate;
        this.cutPoints = cutPoints;
        this.positiveDistribution = positiveDistribution;
        this.negativeDistribution = negativeDistribution;
    }

}
