package datahelper.market;

import po.market.MarketItemPO;
import po.stock.StockItemPO;
import util.exception.StockHasNoDataException;
import util.exception.StockNotFoundException;
import util.exception.TimeException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by kevin on 2017/3/6.
 */
public interface MarketDataHelper {

    /**
     * 获取市场的全部股票代码
     *
     * @return 市场的全部股票代码 ，无为空表
     */
    public List<String> findAllStockCodes();

    /**
     * 获取某只股票的最近交易日条目
     *
     * @param code 股票代码
     * @return 某只股票的最近交易日条目 ，无为null
     * @throws StockNotFoundException  股票不存在
     * @throws StockHasNoDataException 股票无数据
     */
    public MarketItemPO find(String code) throws StockNotFoundException, StockHasNoDataException;

    /**
     * 获取某只股票的某日条目
     *
     * @param code 股票代码
     * @param date 某天
     * @return 某只股票的某日条目
     * @throws StockNotFoundException  股票不存在
     * @throws StockHasNoDataException 股票无数据
     * @throws TimeException 输入的日期不是该股票的交易日
     */
    public MarketItemPO find(String code, LocalDate date) throws StockNotFoundException, StockHasNoDataException, TimeException;

    /**
     * 获得某天的股票信息列表（用于分析当日股票市场的行情）
     *
     * @param date 某一天
     * @return date当天的股票条目 ，没有返回空表
     */
    public List<MarketItemPO> find(LocalDate date);

}
