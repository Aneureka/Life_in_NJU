package controller.market;

import vo.market.DefaultStockVO;
import vo.market.MarketVO;
import vo.stock.StockVO;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by kevin on 2017/3/6.
 */
public interface MarketController {

    /**
     * 查询某段时间（可以是某一天）的股票交易市场行情，行情信息包括但不限于
     * 当日总交易量、涨停股票数、跌停股票数、涨幅超过5%的股票数，跌幅超过 5%的股票数，
     * 开盘‐收盘大于 5%*上一个交易日收盘价的股票个数、开盘‐收盘小于‐5%*上一个交易日收盘价的股票个数。
     *
     * @param date 某天
     * @return 某天的市场行情信息
     * @throws IOException IO异常
     */
    public MarketVO getMarketInfo(LocalDate date) throws IOException;

    /**
     * 获取市场中的全部股票
     *
     * @return 市场中的全部股票，无为空表
     */
    public List<DefaultStockVO> getAllStocks() throws IOException;

}
