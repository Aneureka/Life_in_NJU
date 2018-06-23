package bl.market;

import po.market.MarketItemPO;
import vo.market.DefaultStockVO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kevin on 2017/3/17.
 */
public class Market {

    protected List<MarketItemPO> marketItemPOList;

    /**
     * 构造器
     *
     * @param marketItemPOList 某天的市场条目列表
     */
    public Market(List<MarketItemPO> marketItemPOList){
        this.marketItemPOList = marketItemPOList;
    }

    /**
     * 转为PO
     *
     * @return 某天的市场条目列表
     */
    public List<MarketItemPO> asPO(){
        return marketItemPOList;
    }

    /**
     * 获取市场所有股票
     *
     * @return 市场所有股票
     */
    public List<DefaultStockVO> getAllStocks(){
        List<DefaultStockVO> res = new ArrayList<>(marketItemPOList.size());

        for (MarketItemPO stock: marketItemPOList) {
            //获取stockVO的属性值
            String code = stock.getCurrent().getCode();
            String name = stock.getCurrent().getName();
            double close = stock.getCurrent().getClose();
            double change = new MarketItem(stock).getChange();
            double increase = new MarketItem(stock).getIncrease();
            double volume = stock.getCurrent().getVolume();
            double high = stock.getCurrent().getHigh();
            double low = stock.getCurrent().getLow();
            String market = stock.getCurrent().getMarket();

            res.add(new DefaultStockVO(code, name, close, change, increase, volume, high, low, market));
        }

        return res;
    }

}
