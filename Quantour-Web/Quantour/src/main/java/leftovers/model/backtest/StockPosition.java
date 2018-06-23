package leftovers.model.backtest;

/**
 * Created by Hiki on 2017/6/9.
 */
public class StockPosition {

    // 时间戳
    private String date;

    // 该持仓的买入均价
    private double avg_price;

    // 该持仓的成交价
    private double last_price;

    // 当前仓位市值
    private double market_value;

    // 股票代码
    private String order_book_id;

    // 股票名称
    private String symbol;

    // 买入数量
    private long quantity;



    public StockPosition() {
    }

    public StockPosition(String date, double avg_price, double last_price, double market_value, String order_book_id, long quantity, String symbol) {
        this.date = date;
        this.avg_price = avg_price;
        this.last_price = last_price;
        this.market_value = market_value;
        this.order_book_id = order_book_id;
        this.quantity = quantity;
        this.symbol = symbol;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getAvg_price() {
        return avg_price;
    }

    public void setAvg_price(double avg_price) {
        this.avg_price = avg_price;
    }

    public double getLast_price() {
        return last_price;
    }

    public void setLast_price(double last_price) {
        this.last_price = last_price;
    }

    public double getMarket_value() {
        return market_value;
    }

    public void setMarket_value(double market_value) {
        this.market_value = market_value;
    }

    public String getOrder_book_id() {
        return order_book_id;
    }

    public void setOrder_book_id(String order_book_id) {
        this.order_book_id = order_book_id;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
}
