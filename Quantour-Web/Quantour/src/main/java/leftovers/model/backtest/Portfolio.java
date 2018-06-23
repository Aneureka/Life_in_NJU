package leftovers.model.backtest;

/**
 * Created by Hiki on 2017/6/9.
 */
public class Portfolio {

    // 时间戳
    private String date;

    // 可用资金
    private double cash;

    // 市值
    private double market_value;

    // 总权益
    private double total_value;

    // 净值
    private double static_unit_net_value;

    // 实时净值
    private double unit_net_value;

    // 份额
    private double units;

    public Portfolio() {
    }

    public Portfolio(String date, double cash, double market_value, double total_value, double static_unit_net_value, double unit_net_value, double units) {
        this.date = date;
        this.cash = cash;
        this.market_value = market_value;
        this.total_value = total_value;
        this.static_unit_net_value = static_unit_net_value;
        this.unit_net_value = unit_net_value;
        this.units = units;
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

    public double getStatic_unit_net_value() {
        return static_unit_net_value;
    }

    public void setStatic_unit_net_value(double static_unit_net_value) {
        this.static_unit_net_value = static_unit_net_value;
    }

    public double getUnit_net_value() {
        return unit_net_value;
    }

    public void setUnit_net_value(double unit_net_value) {
        this.unit_net_value = unit_net_value;
    }

    public double getUnits() {
        return units;
    }

    public void setUnits(double units) {
        this.units = units;
    }
}
