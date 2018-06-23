package po.market;

import po.stock.StockPO;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by Hiki on 2017/3/8.
 */
public class MarketPO {

    /**
     * 股票列表
     */
    private List<StockPO> stockList;

    /**
     * 市场名称
     */
    private String name;

    /**
     * 构造函数
     * @param stockList
     * @param name
     */
    public MarketPO(List<StockPO> stockList, String name) {
        this.stockList = stockList;
        this.name = name;
    }

}
