package blservice.stockblservice;

import util.exception.StockNotFoundException;
import util.exception.TimeException;
import vo.stock.StockVO;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * Created by Hiki on 2017/3/8.
 */
public interface StockBLService {


    /**
     * 时间为默认最近一个月（交易日）的数据
     * @param name
     * @return
     */
    public StockVO findStockByName(String name) throws StockNotFoundException, TimeException;


    /**
     * 时间为默认最近一个月（交易日）的数据
     * @param code
     * @return
     */
    public StockVO findStockByCode(String code) throws TimeException, StockNotFoundException;

    /**
     * 用股票名称查询某只股票一段时间内的股票信息，包括StockItemList信息，Stock简略信息
     *
     * @param name      the name
     * @param beginTime the begin time
     * @param endTime   the end time
     * @return stock vo
     */
    public StockVO findStockByName(String name, LocalDate beginTime, LocalDate endTime) throws StockNotFoundException, TimeException;

    /**
     * 用股票代码查询某只股票一段时间内的股票信息，包括StockItemList信息，Stock简略信息
     *
     * @param code      the code
     * @param beginTime the begin time
     * @param endTime   the end time
     * @return stock vo
     */
    public StockVO findStockByCode(String code, LocalDate beginTime, LocalDate endTime) throws StockNotFoundException, TimeException;


    /**
     * 根据股票代码返回月K线数据
     * @param code
     * @param beginTime
     * @param endTime
     * @return
     * @throws TimeException
     * @throws StockNotFoundException
     */
    public StockVO findMonthStockByCode(String code, LocalDate beginTime, LocalDate endTime) throws TimeException, StockNotFoundException;

    /**
     * 根据股票名称返回月K线数据
     * @param name
     * @param beginTime
     * @param endTime
     * @return
     * @throws TimeException
     * @throws StockNotFoundException
     */
    public StockVO findMonthStockByName(String name, LocalDate beginTime, LocalDate endTime) throws TimeException, StockNotFoundException;

    /**
     * 根据股票代码返回周K线数据
     * @param code
     * @param beginTime
     * @param endTime
     * @return
     * @throws TimeException
     * @throws StockNotFoundException
     */
    public StockVO findWeekStockByCode(String code, LocalDate beginTime, LocalDate endTime) throws TimeException, StockNotFoundException;

    /**
     * 根据股票名称返回周K线数据
     * @param name
     * @param beginTime
     * @param endTime
     * @return
     * @throws TimeException
     * @throws StockNotFoundException
     */
    public StockVO findWeekStockByName(String name, LocalDate beginTime, LocalDate endTime) throws TimeException, StockNotFoundException;


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
