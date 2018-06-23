package util.tool;

import java.util.List;

/**
 * Created by kevin on 2017/3/17.
 */
public class DistributionAnalyse {

    /**
     * 分析数据的分布情况
     *
     * @param data 数据
     * @param cutPoints 分割点
     * @return 数据的分布情况
     */
    public static int[] analyse(List<Double> data, double[] cutPoints){
        int[] res = new int[cutPoints.length - 1];

        //获取数据的分布
        for (double num: data) {
            //获取数据分布位置
            int position = getPosition(cutPoints, num);
            //数据分布位置加一
            if (position != -1){
                ++res[position];
            }
        }

        return res;
    }

    /**
     * 分析数据的分布情况，例如分析{1, 2, 3, 4, 5, 6}在[0, 10]上五等分的分布情况
     *
     * @param data 数据
     * @param range 分布范围
     * @param cut 分布范围的切割份数
     * @return 数据的分布情况
     */
    public static int[] analyse(List<Double> data, double[] range, int cut){
        //获取分割点
        double[] cutPoints = getCutPoints(range, cut);

        return analyse(data, cutPoints);
    }

    /**
     * 分析数据的分布情况，例如分析{1, 2, 3, 4, 5, 6}在[0, 10]上五等分的分布情况
     *
     * @param data 数据
     * @param lowerBound 下界
     * @param upperBound 上界
     * @param cut 分布范围的切割份数
     * @return 数据的分布情况
     */
    public static int[] analyse(List<Double> data, double lowerBound, double upperBound, int cut){
        //获取分割点
        double[] cutPoints = getCutPoints(lowerBound, upperBound, cut);

        return analyse(data, cutPoints);
    }

    /**
     * 获取数字的分布位置
     *
     * @param cutPoints 分割点
     * @param num 数字
     * @return 数字的分布位置
     */
    private static int getPosition(double[] cutPoints, double num){
        for (int i = 0; i < cutPoints.length - 1; i++) {
            if (cutPoints[i] < num && num <= cutPoints[i + 1]){
                return i;
            }
        }

        return -1;
    }

    /**
     * 获取分割点
     *
     * @param range 分布范围
     * @param cut 分布范围的分割份数
     * @return 分割点
     */
    public static double[] getCutPoints(double[] range, int cut){
        return getCutPoints(range[0], range[1], cut);
    }

    /**
     * 获取分割点
     *
     * @param  lowerBound 下界
     * @param upperBound 上界
     * @param cut 分布范围的分割份数
     * @return 分割点
     */
    public static double[] getCutPoints(double lowerBound, double upperBound, int cut){
        double[] res = new double[cut + 1];

        double singleLength = (upperBound - lowerBound) / cut;

        res[0] = lowerBound;
        res[cut] = upperBound;

        for (int i = 1; i < cut; i++) {
            res[i] = res[i - 1] + singleLength;
        }

        return res;
    }

}
