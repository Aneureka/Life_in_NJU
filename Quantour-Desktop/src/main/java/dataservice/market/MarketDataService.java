package dataservice.market;

import bl.market.MarketItem;
import po.market.MarketItemPO;
import po.stock.StockItemPO;
import util.exception.StockHasNoDataException;
import util.exception.StockNotFoundException;
import util.exception.TimeException;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by Hiki on 2017/3/8.
 */
public interface MarketDataService {

    /**
     * 获得某天的市场条目列表
     *
     * @param date 某一天
     * @return 某天的市场条目列表 list
     */
    public List<MarketItemPO> findMarketItemsByTime(LocalDate date);

    /**
     * 获取所有股票的最近一个交易日的市场条目
     *
     * @return 所有股票的最近一个交易日的市场条目
     */
    public List<MarketItemPO> findLatestMIsOfAllStocks();

}
