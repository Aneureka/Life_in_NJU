package po.market;

import po.stock.StockItemPO;

/**
 * Created by kevin on 2017/3/10.
 */
public class MarketItemPO {

    private StockItemPO current;
    private StockItemPO previous;

    /**
     * 构造器
     *
     * @param current  当天股票条目
     * @param previous 前一天股票条目
     */
    public MarketItemPO(StockItemPO current, StockItemPO previous){
        this.current = current;
        this.previous = previous;
    }

    /**
     * 获取当天股票条目
     *
     * @return 当天股票条目
     */
    public StockItemPO getCurrent() {
        return current;
    }

    /**
     * 获取前一天股票条目
     *
     * @return 前一天股票条目
     */
    public StockItemPO getPrevious() {
        return previous;
    }

}
