package dao.market;

import datahelper.market.MarketDataHelper;
import datahelper.market.MarketDataHelperFromFile;
import datahelper.market.MarketDataHelperFromMemory;
import dataservice.market.MarketDataService;
import po.market.MarketItemPO;
import po.stock.StockItemPO;
import util.exception.StockHasNoDataException;
import util.exception.StockNotFoundException;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kevin on 2017/3/6.
 */
public class MarketDao implements MarketDataService {

    /**
     * 帮助MarketDao从不同的来源读取数据
     */
    MarketDataHelper marketDataHelper = new MarketDataHelperFromMemory();

    /**
     * 获得某天的市场条目列表
     *
     * @param date 某一天
     * @return 某天的市场条目列表
     */
    public List<MarketItemPO> findMarketItemsByTime(LocalDate date){
        return marketDataHelper.find(date);
    }

    /**
     * 获取所有股票的最近一个交易日的市场条目
     *
     * @return 所有股票的最近一个交易日的市场条目
     */
    public List<MarketItemPO> findLatestMIsOfAllStocks(){
        List<MarketItemPO> res = new ArrayList<>();

        //获取所有股票代码
        List<String> codes = marketDataHelper.findAllStockCodes();

        //获取所有股票的市场条目
        for (String code : codes) {
            try {
                res.add(marketDataHelper.find(code));
            } catch (StockNotFoundException e) {
                e.printStackTrace();
            } catch (StockHasNoDataException e) {
                e.printStackTrace();
            }
        }

        return res;
    }

}
