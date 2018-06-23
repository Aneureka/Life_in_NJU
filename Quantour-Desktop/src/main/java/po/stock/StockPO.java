package po.stock;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by kevin on 2017/3/6.
 */
public class StockPO {

    /**
     * 股票信息列表
     */
    private List<StockItemPO> StockItemList;


    public StockPO(List<StockItemPO> stockItemList) {
        StockItemList = stockItemList;
    }


    public List<StockItemPO> getStockItemList() {
        return StockItemList;
    }

    public void setStockItemList(List<StockItemPO> stockItemList) {
        StockItemList = stockItemList;
    }
}