package vo.backtest;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by 97257 on 2017/4/2.
 */
public class PositionRecordVO {

    /**
     * 交易日期
     */
    private LocalDate date;

    /**
     * 头寸
     */
    private double capitalAll;

    /**
     * 现金
     */
    private double capitalPresent;


    /**
     * 总资产盈亏
     */
    private double allProfit;


    /**
     * 持有股票信息
     */
    private List<StockHoldingVO> stockHoldings;

    public PositionRecordVO(LocalDate date, double capitalAll, double capitalPresent, double allProfit, List<StockHoldingVO> stockHoldings) {
        this.date = date;
        this.capitalAll = capitalAll;
        this.capitalPresent = capitalPresent;
        this.allProfit = allProfit;
        this.stockHoldings = stockHoldings;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getCapitalAll() {
        return capitalAll;
    }

    public void setCapitalAll(double capitalAll) {
        this.capitalAll = capitalAll;
    }

    public double getCapitalPresent() {
        return capitalPresent;
    }

    public void setCapitalPresent(double capitalPresent) {
        this.capitalPresent = capitalPresent;
    }

    public List<StockHoldingVO> getStockHoldings() {
        return stockHoldings;
    }

    public void setStockHoldings(List<StockHoldingVO> stockHoldings) {
        this.stockHoldings = stockHoldings;
    }

    public double getAllProfit() {
        return allProfit;
    }

    public void setAllProfit(double allProfit) {
        this.allProfit = allProfit;
    }

    public String getStockHoldingsSize() {
        return String.valueOf(stockHoldings.size());
    }
}
