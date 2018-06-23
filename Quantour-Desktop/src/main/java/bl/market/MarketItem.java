package bl.market;

import po.market.MarketItemPO;

/**
 * Created by kevin on 2017/3/10.
 */
public class MarketItem {

    private MarketItemPO marketItemPO;

    public MarketItem(MarketItemPO marketItemPO){
        this.marketItemPO = marketItemPO;
    }

    /**
     * 获取涨幅
     *
     * @return 涨幅，正为涨，负为跌，0表示不存在上一交易日
     */
    public double getIncrease(){
        //不存在上一交易日时，涨幅为零
        if (!hasPrevious() || marketItemPO.getCurrent() == null)
            return 0;

        //获取当日收盘价，上一交易日收盘价
        double currentClose = marketItemPO.getCurrent().getAdjClose();
        double previousClose = marketItemPO.getPrevious().getAdjClose();

        return (currentClose - previousClose) / previousClose;
    }

    /**
     * 获取涨跌值
     *
     * @return 涨跌值
     */
    public double getChange(){
        return hasPrevious() ? marketItemPO.getCurrent().getAdjClose() - marketItemPO.getPrevious().getAdjClose() : 0;
    }

    public double getOCDividePC(){
        //不存在上一交易日时，返回零
        if (!hasPrevious())
            return 0;

        //获取当日开盘价，当日收盘价，上一交易日收盘价
        double currentOpen = marketItemPO.getCurrent().getOpen();
        double currentClose = marketItemPO.getCurrent().getAdjClose();
        double previousClose = marketItemPO.getPrevious().getAdjClose();

        return (currentOpen - currentClose) / previousClose;
    }

    /**
     * 判断是否有上一交易日
     *
     * @return true有上一交易日，false无上一交易日
     */
    private boolean hasPrevious(){
        return marketItemPO.getPrevious() != null;
    }

}
