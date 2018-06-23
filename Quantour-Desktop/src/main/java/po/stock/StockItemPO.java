package po.stock;

import java.time.LocalDate;

/**
 * Created by Hiki on 2017/3/8.
 */
public class StockItemPO {

    /**
     * 股票序列号
     */
    private long serial;

    /**
     * 股票日期
     */
    private LocalDate date;

    /**
     * 开盘价
     */
    private double open;

    /**
     * 最高价
     */
    private double high;

    /**
     * 最低价
     */
    private double low;

    /**
     * 收盘价
     */
    private double close;

    /**
     * 当日成交量
     */
    private double volume;

    /**
     * 复权收盘价
     */
    private double adjClose;

    /**
     * 股票代码
     */
    private String code;

    /**
     * 股票名称
     */
    private String name;

    /**
     * 股票市场名称
     */
    private String market;

    /**
     * 构造函数
     */
    public StockItemPO(long serial, LocalDate date, double open, double high, double low, double close, double volume, double adjClose, String code, String name, String market) {
        this.serial = serial;
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
    }

    /**
     * 默认构造器
     */
    public StockItemPO(){
        this.serial = -1;
        this.date = LocalDate.MIN;
        this.open = 0;
        this.high = 0;
        this.low = 0;
        this.close = 0;
        this.volume = 0;
        this.adjClose = 0;
        this.code = "";
        this.name = "";
        this.market = "";
    }

    /**
     * 将字符串转为StockItemPO
     *
     * @param stockItem 字符串
     * @return StockItemPO
     */
    public static StockItemPO parse(String stockItem){
        StockItemPO stockItemPO = new StockItemPO();

        //分割字符串，获得各个属性值
        String[] stockItemAttributes = stockItem.split("\t");

        //设置属性值
        stockItemPO.setSerial(Long.parseLong(stockItemAttributes[0]));
        stockItemPO.setDate(LocalDate.parse(stockItemAttributes[1]));
        stockItemPO.setOpen(Double.parseDouble(stockItemAttributes[2]));
        stockItemPO.setHigh(Double.parseDouble(stockItemAttributes[3]));
        stockItemPO.setLow(Double.parseDouble(stockItemAttributes[4]));
        stockItemPO.setClose(Double.parseDouble(stockItemAttributes[5]));
        stockItemPO.setVolume(Long.parseLong(stockItemAttributes[6]));
        stockItemPO.setAdjClose(Double.parseDouble(stockItemAttributes[7]));
        stockItemPO.setCode(stockItemAttributes[8]);
        stockItemPO.setName(stockItemAttributes[9]);
        stockItemPO.setMarket(stockItemAttributes[10]);

        return stockItemPO;
    }

    public long getSerial() {
        return serial;
    }

    public void setSerial(long serial) {
        this.serial = serial;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getOpen() {
        return open;
    }

    public void setOpen(double open) {
        this.open = open;
    }

    public double getHigh() {
        return high;
    }

    public void setHigh(double high) {
        this.high = high;
    }

    public double getLow() {
        return low;
    }

    public void setLow(double low) {
        this.low = low;
    }

    public double getClose() {
        return close;
    }

    public void setClose(double close) {
        this.close = close;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(long volume) {
        this.volume = volume;
    }

    public double getAdjClose() {
        return adjClose;
    }

    public void setAdjClose(double adjClose) {
        this.adjClose = adjClose;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMarket() {
        return market;
    }

    public void setMarket(String market) {
        this.market = market;
    }
}
