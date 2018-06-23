package vo.stock;

import vo.stock.StockItemVO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kevin on 2017/3/6.
 */
public class StockVO {

    /**
     * 股票信息列表
     */
    public List<StockItemVO> stockItemList;

    /**
     * 当前价（最近时间的价格）
     */
    public double presentPrice;

    /**
     * 涨跌额
     */
    public double change;

    /**
     * 涨跌幅
     */
    public double increase;

    /**
     * 对数收益率方差
     */
    public double logReturnVariance;



    /**
     * 构造函数
     * @param stockItemList
     * @param presentPrice
     * @param change
     * @param increase
     */
    public StockVO(List<StockItemVO> stockItemList, double presentPrice, double change, double increase, double logReturnVariance) {
        this.stockItemList = stockItemList;
        this.presentPrice = presentPrice;
        this.change = change;
        this.increase = increase;
        this.logReturnVariance = logReturnVariance;
    }

    /**
     * 默认构造函数
     */
    public StockVO() {
        this.stockItemList = new ArrayList<>();
        this.presentPrice = 0.0;
        this.change = 0.0;
        this.increase = 0.0;
        this.logReturnVariance = 0.0;
    }

    public List<StockItemVO> getStockItemList() {
        return stockItemList;
    }

    public double getPresentPrice() {
        return presentPrice;
    }

    public double getChange() {
        return change;
    }

    public double getIncrease() {
        return increase;
    }

    public double getLogReturnVariance() {
        return logReturnVariance;
    }
}
