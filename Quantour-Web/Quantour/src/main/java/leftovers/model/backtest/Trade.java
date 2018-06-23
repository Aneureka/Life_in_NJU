package leftovers.model.backtest;

import javax.ejb.Local;
import java.time.LocalDateTime;

/**
 * Created by Hiki on 2017/6/9.
 */
public class Trade {

    // 交易时间
    private LocalDateTime datetime;

    // 手续费
    private double commission;

    // 执行ID
    private String exec_id;

    // 成交价
    private double last_price;

    // 成交量
    private double last_quantity;

    // 合约代码
    private String order_book_id;

    // 交易ID（与exec_id相同）
    private String order_id;

    // 买/卖
    private String side;

    // 股票名称
    private String symbol;

    // 税
    private double tax;

    // 交易时间
    private LocalDateTime trading_datetime;

    // 费用
    private double transaction_cost;

    public Trade() {
    }

    public Trade(LocalDateTime datetime, double commission, String exec_id, double last_price, double last_quantity, String order_book_id, String order_id, String side, String symbol, double tax, LocalDateTime trading_datetime, double transaction_cost) {
        this.datetime = datetime;
        this.commission = commission;
        this.exec_id = exec_id;
        this.last_price = last_price;
        this.last_quantity = last_quantity;
        this.order_book_id = order_book_id;
        this.order_id = order_id;
        this.side = side;
        this.symbol = symbol;
        this.tax = tax;
        this.trading_datetime = trading_datetime;
        this.transaction_cost = transaction_cost;
    }


    public LocalDateTime getDatetime() {
        return datetime;
    }

    public void setDatetime(LocalDateTime datetime) {
        this.datetime = datetime;
    }

    public double getCommission() {
        return commission;
    }

    public void setCommission(double commission) {
        this.commission = commission;
    }

    public String getExec_id() {
        return exec_id;
    }

    public void setExec_id(String exec_id) {
        this.exec_id = exec_id;
    }

    public double getLast_price() {
        return last_price;
    }

    public void setLast_price(double last_price) {
        this.last_price = last_price;
    }

    public double getLast_quantity() {
        return last_quantity;
    }

    public void setLast_quantity(double last_quantity) {
        this.last_quantity = last_quantity;
    }

    public String getOrder_book_id() {
        return order_book_id;
    }

    public void setOrder_book_id(String order_book_id) {
        this.order_book_id = order_book_id;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    public LocalDateTime getTrading_datetime() {
        return trading_datetime;
    }

    public void setTrading_datetime(LocalDateTime trading_datetime) {
        this.trading_datetime = trading_datetime;
    }

    public double getTransaction_cost() {
        return transaction_cost;
    }

    public void setTransaction_cost(double transaction_cost) {
        this.transaction_cost = transaction_cost;
    }
}
