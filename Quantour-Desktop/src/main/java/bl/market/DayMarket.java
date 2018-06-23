package bl.market;

import po.market.MarketItemPO;
import po.stock.StockItemPO;
import util.tool.DistributionAnalyse;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kevin on 2017/3/10.
 */
public class DayMarket extends Market {

    /**
     * 构造器
     *
     * @param marketItemPOList 某天的市场条目列表
     */
    public DayMarket(List<MarketItemPO> marketItemPOList) {
        super(marketItemPOList);
    }

    /**
     * 获得市场当日总交易量
     *
     * @return 市场当日总交易量
     */
    public double getDayVolumn() {
        double res = 0;

        for (MarketItemPO marketItemPO : marketItemPOList) {
            res += marketItemPO.getCurrent().getVolume();
        }

        return res;
    }


    /**
     * 获得市场当日涨停股票数
     *
     * @return 市场当日涨停股票数
     */
    public long getSLSNum() {
        long res = 0;

        for (MarketItemPO marketItemPO : marketItemPOList) {
            if (new MarketItem(marketItemPO).getIncrease() >= 0.1) {
                ++res;
            }
        }

        return res;
    }


    /**
     * 获得当日跌停股票数
     *
     * @return 当日跌停股票数
     */
    public long getDLSNum() {
        long res = 0;

        for (MarketItemPO marketItemPO : marketItemPOList) {
            if (new MarketItem(marketItemPO).getIncrease() <= -0.1) {
                ++res;
            }
        }

        return res;
    }


    /**
     * 获得当日涨幅超过5%的股票数
     *
     * @return 当日涨幅超过5%的股票数
     */
    public long getSFSNum() {
        long res = 0;

        for (MarketItemPO marketItemPO : marketItemPOList) {
            if (new MarketItem(marketItemPO).getIncrease() >= 0.05) {
                ++res;
            }
        }

        return res;
    }


    /**
     * 获得当日跌幅超过5%的股票数
     *
     * @return 当日跌幅超过5%的股票数
     */
    public long getDFSNum() {
        long res = 0;

        for (MarketItemPO marketItemPO : marketItemPOList) {
            if (new MarketItem(marketItemPO).getIncrease() <= -0.05) {
                ++res;
            }
        }

        return res;
    }

    /**
     * 获得开盘‐收盘大于5%*上一个交易日收盘价的股票个数
     *
     * @return 开盘‐收盘大于5%*上一个交易日收盘价的股票个数
     */
    public long getOCHFNum() {
        long res = 0;

        for (MarketItemPO marketItemPO : marketItemPOList) {
            if (new MarketItem(marketItemPO).getOCDividePC() > 0.05) {
                ++res;
            }
        }

        return res;
    }


    /**
     * 获得开盘‐收盘小于5%*上一个交易日收盘价的股票个数
     *
     * @return 开盘‐收盘小于5%*上一个交易日收盘价的股票个数
     */
    public long getOCLNFNum() {
        long res = 0;

        for (MarketItemPO marketItemPO : marketItemPOList) {
            if (new MarketItem(marketItemPO).getOCDividePC() < 0.05) {
                ++res;
            }
        }

        return res;
    }

    /**
     * 获取市场中股票的涨幅分布
     *
     * @return
     */
    public int[] getIncreaseDistribution(){
        //获取全部涨幅数据
        List<Double> increases = new ArrayList<>(marketItemPOList.size());
        for (MarketItemPO marketItemPO : marketItemPOList) {
            increases.add(new MarketItem(marketItemPO).getIncrease());
        }

        //分割点
        double[] cutPoints = {-1, -0.1, -0.08, -0.06, -0.04, -0.02, 0, 0.02, 0.04, 0.06, 0.08, 0.1, 1};

        //分析涨幅分布
        return DistributionAnalyse.analyse(increases, cutPoints);
    }


}
