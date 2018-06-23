package vo.market;

import util.tool.NumberFormatter;

import java.time.LocalDate;

/**
 * Created by kevin on 2017/3/11.
 */
public class DefaultStockVO {

    /**
     * 股票代码
     */
    public String code;

    /**
     * 股票名称
     */
    public String name;

    /**
     * 收盘价
     */
    public double close;

    /**
     * 涨跌额
     */
    public double change;

    /**
     * 涨跌幅
     */
    public double increase;

    /**
     * 当日成交量
     */
    public double volume;

    /**
     * 最高价
     */
    public double high;

    /**
     * 最低价
     */
    public double low;

    /**
     * 股票市场名称
     */
    public String market;

    public DefaultStockVO(String code, String name, double close, double change, double increase, double volume, double high, double low, String market) {
        this.code = code;
        this.name = name;
        this.close = close;
        this.change = change;
        this.increase = increase;
        this.volume = volume;
        this.high = high;
        this.low = low;
        this.market = market;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public Double getClose() {
        return Double.parseDouble(NumberFormatter.format(close));
    }

    public String getChange() {
        return NumberFormatter.format(change);
    }

    public String getIncrease() {
        return NumberFormatter.formatToPercent(increase) + "%";
    }

    public String getVolume() {
        return NumberFormatter.formatToBaseWan(volume);
    }

    public Double getHigh() {
        return Double.parseDouble(NumberFormatter.format(high));
    }

    public Double getLow() {
        return Double.parseDouble(NumberFormatter.format(low));
    }

    public String getMarket() {
        return market;
    }
}
