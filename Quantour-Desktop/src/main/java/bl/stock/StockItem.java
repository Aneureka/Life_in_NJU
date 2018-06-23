package bl.stock;

import po.stock.StockItemPO;
import vo.stock.StockItemVO;

/**
 * Created by Hiki on 2017/3/10.
 */
public class StockItem {

    /**
     *  将StockItemPO转换成VO，直接转换
     * @param stockItemPO
     * @return
     */
    public StockItemVO toStockItemVO(StockItemPO stockItemPO){
        return new StockItemVO(stockItemPO);
    }


}
