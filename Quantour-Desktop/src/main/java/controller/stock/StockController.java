package controller.stock;

import util.exception.StockNotFoundException;
import util.exception.TimeException;
import vo.stock.StockVO;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by Hiki on 2017/3/13.
 */
public interface StockController {


    /**
     * 时间为默认最近一个月（交易日）的数据
     * @param code
     * @return
     */
    public StockVO findStock(String code) throws TimeException, StockNotFoundException;


    /**
     * 通过索引查找股票
     * @param index
     * @param beginTime
     * @param endTime
     * @return
     * @throws StockNotFoundException
     * @throws TimeException
     */
    public StockVO findStock(String index, LocalDate beginTime, LocalDate endTime) throws StockNotFoundException, TimeException;

    /**
     * 根据索引查找月K线数据
     * @param index
     * @param beginTime
     * @param endTime
     * @return
     * @throws TimeException
     * @throws StockNotFoundException
     */
    public StockVO findMonthStock(String index, LocalDate beginTime, LocalDate endTime) throws TimeException, StockNotFoundException;

    /**
     * 根据索引查找周K线数据
     * @param index
     * @param beginTime
     * @param endTime
     * @return
     * @throws TimeException
     * @throws StockNotFoundException
     */
    public StockVO findWeekStock(String index, LocalDate beginTime, LocalDate endTime) throws TimeException, StockNotFoundException;


    /**
     * 通过查询获得建议的股票代码和名称列表，其中代码优先级高
     * @param query
     * @return
     * @throws StockNotFoundException
     */
    public List<String> findSuggestions(String query) throws StockNotFoundException;

    /**
     * 获取行业列表
     * @return
     */
    public List<String> getSections();


    /**
     * 根据行业查找股票代码列表
     * @param sectionName
     * @return
     */
    public List<String> getStockCodesBySection(String sectionName);


    /**
     * 根据股票代码查询股票行业
     * @param code
     * @return
     */
    public String getSectionByStockCode(String code);
}
