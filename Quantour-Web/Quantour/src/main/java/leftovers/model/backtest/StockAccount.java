package leftovers.model.backtest;

/**
 * Created by Hiki on 2017/6/9.
 */
public class StockAccount {

    // 时间戳
    private String date;

    // 可用资金
    private double cash;

    // 投资组合在分红现金收到账面之前的应收分红部分
    private double dividend_receivable;

    // 市值
    private double market_value;

    // 总权益
    private double total_value;

    // 总费用
    private double transaction_cost;

    public StockAccount() {
    }

    public StockAccount(String date, double cash, double dividend_receivable, double market_value, double total_value, double transaction_cost) {
        this.date = date;
        this.cash = cash;
        this.dividend_receivable = dividend_receivable;
        this.market_value = market_value;
        this.total_value = total_value;
        this.transaction_cost = transaction_cost;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getCash() {
        return cash;
    }

    public void setCash(double cash) {
        this.cash = cash;
    }

    public double getDividend_receivable() {
        return dividend_receivable;
    }

    public void setDividend_receivable(double dividend_receivable) {
        this.dividend_receivable = dividend_receivable;
    }

    public double getMarket_value() {
        return market_value;
    }

    public void setMarket_value(double market_value) {
        this.market_value = market_value;
    }

    public double getTotal_value() {
        return total_value;
    }

    public void setTotal_value(double total_value) {
        this.total_value = total_value;
    }

    public double getTransaction_cost() {
        return transaction_cost;
    }

    public void setTransaction_cost(double transaction_cost) {
        this.transaction_cost = transaction_cost;
    }
}
