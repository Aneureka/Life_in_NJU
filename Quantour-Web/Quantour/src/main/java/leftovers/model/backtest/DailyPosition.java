package leftovers.model.backtest;

import java.time.LocalDateTime;

/**
 * Created by Hiki on 2017/6/11.
 */
public class DailyPosition {

    // 时间
    private LocalDateTime date_time;

    // 合约代码
    private String order_book_id;

    // 股票名称
    private String symbol;

    // 结算价
    private double last_price;

    // 仓位
    private long quantity;

    // 开仓均价
    private double avg_price;

    // 市值
    private double market_value;

//    // 累计盈亏
//    private double total_pnls;
//
//    // 费用
//    private double transaction_cost;


    public DailyPosition(LocalDateTime date_time, String order_book_id, String symbol, double last_price, long quantity, double avg_price, double market_value) {
        this.date_time = date_time;
        this.order_book_id = order_book_id;
        this.symbol = symbol;
        this.last_price = last_price;
        this.quantity = quantity;
        this.avg_price = avg_price;
        this.market_value = market_value;
    }

    public LocalDateTime getDate_time() {
        return date_time;
    }

    public void setDate_time(LocalDateTime date_time) {
        this.date_time = date_time;
    }

    public String getOrder_book_id() {
        return order_book_id;
    }

    public void setOrder_book_id(String order_book_id) {
        this.order_book_id = order_book_id;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public double getLast_price() {
        return last_price;
    }

    public void setLast_price(double last_price) {
        this.last_price = last_price;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public double getAvg_price() {
        return avg_price;
    }

    public void setAvg_price(double avg_price) {
        this.avg_price = avg_price;
    }

    public double getMarket_value() {
        return market_value;
    }

    public void setMarket_value(double market_value) {
        this.market_value = market_value;
    }


}
