package vo.stock;

import po.stock.StockItemPO;
import util.tool.StockCommonInfo;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by kevin on 2017/3/8.
 */
public class StockItemVO {

    /**
     * 股票日期
     */
    public LocalDate date;

    /**
     * 开盘价
     */
    public double open;

    /**
     * 最高价
     */
    public double high;

    /**
     * 最低价
     */
    public double low;

    /**
     * 收盘价
     */
    public double close;

    /**
     * 当日成交量
     */
    public double volume;

    /**
     * 复权收盘价
     */
    public double adjClose;

    /**
     * 股票代码
     */
    public String code;

    /**
     * 股票名称
     */
    public String name;

    /**
     * 股票市场名称
     */
    public String market;

    /**
     * 涨跌额
     */
    public double change;

    /**
     * 涨跌幅
     */
    public double increase;

    /**
     * 对数收益率
     */
    public double logReturn;

    /**
     * 均值：5天，10天，30天，60天，120天，240天
     */
    public Map<Integer, Double> averages;



    /**
     * 直接进行PO<->VO转换
     * @param stockItemPO
     */
    public StockItemVO(StockItemPO stockItemPO) {
        this.date = stockItemPO.getDate();
        this.open = stockItemPO.getOpen();
        this.high = stockItemPO.getHigh();
        this.low = stockItemPO.getLow();
        this.close = stockItemPO.getClose();
        this.volume = stockItemPO.getVolume();
        this.adjClose = stockItemPO.getAdjClose();
        this.code = stockItemPO.getCode();
        this.name = stockItemPO.getName();
        this.market = stockItemPO.getMarket();
        this.change = 0.0;
        this.increase = 0.0;
        this.logReturn = 0.0;
        this.averages = new HashMap<>();
        for (int days : StockCommonInfo.AVERAGE_DAY) {
            averages.put(days, 0.0);
        }

    }

    public StockItemVO(LocalDate date, double open, double high, double low, double close, double volume, double adjClose, String code, String name, String market, double change, double increase, double logReturn, Map<Integer, Double> averages) {
        this.date = date;
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
        this.volume = volume;
        this.adjClose = adjClose;
        this.code = code;
        this.name = name;
        this.market = market;
        this.change = change;
        this.increase = increase;
        this.logReturn = logReturn;
        this.averages = averages;
    }

    public StockItemVO(){
        this.date = LocalDate.MIN;
        this.open = 0.0;
        this.high = 0.0;
        this.low = 0.0;
        this.close = 0.0;
        this.volume = 0.0;
        this.adjClose = 0.0;
        this.code = "";
        this.name = "";
        this.market = "";
        this.change = 0.0;
        this.increase = 0.0;
        this.logReturn = 0.0;
        this.averages = new HashMap<>();
        for (int days : StockCommonInfo.AVERAGE_DAY) {
            averages.put(days, 0.0);
        }
    }

}
