package leftovers.model.backtest;

import java.time.LocalDateTime;

/**
 * Created by Hiki on 2017/6/11.
 */
public class TradeRecord {

    // 交易时间
    private LocalDateTime trade_date_time;

    // 合约代码
    private String order_book_id;

    // 股票名称
    private String symbol;

    // 买/卖
    private String side;

    // 成交量
    private double last_quantity;

    // 成交价
    private double last_price;

    // 费用（手续费）
    private double transaction_cost;

    public TradeRecord() {
    }

    public TradeRecord(LocalDateTime trade_date_time, String order_book_id, String symbol, String side, double last_quantity, double last_price, double transaction_cost) {
        this.trade_date_time = trade_date_time;
        this.order_book_id = order_book_id;
        this.symbol = symbol;
        this.side = side;
        this.last_quantity = last_quantity;
        this.last_price = last_price;
        this.transaction_cost = transaction_cost;
    }

    public LocalDateTime getTrade_date_time() {
        return trade_date_time;
    }

    public void setTrade_date_time(LocalDateTime trade_date_time) {
        this.trade_date_time = trade_date_time;
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

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public double getLast_quantity() {
        return last_quantity;
    }

    public void setLast_quantity(double last_quantity) {
        this.last_quantity = last_quantity;
    }

    public double getLast_price() {
        return last_price;
    }

    public void setLast_price(double last_price) {
        this.last_price = last_price;
    }

    public double getTransaction_cost() {
        return transaction_cost;
    }

    public void setTransaction_cost(double transaction_cost) {
        this.transaction_cost = transaction_cost;
    }
}
