package dataservice.stock;

import po.stock.StockAttributesPO;
import util.exception.StockNotFoundException;
import po.stock.StockPO;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Map;

/**
 * Created by Hiki on 2017/3/8.
 */
public interface StockDataService {


    /**
     * 根据股票代码查询股票信息列表
     * @param code
     * @return
     * @throws StockNotFoundException
     */
    public StockPO findStockPOByCode(String code) throws StockNotFoundException;

//    /**
//     * 根据股票名称查询股票信息列表
//     * @param name
//     * @return
//     * @throws StockNotFoundException
//     */
//    public StockPO findStockPOByName(String name) throws StockNotFoundException;


    /**
     * 通过股票名称查找股票代码
     * @param name
     * @return
     * @throws IOException
     */
    public String findCodeByName(String name) throws StockNotFoundException;


    /**
     * 查找code-name映射关系
     * @return
     * @throws StockNotFoundException
     */
    public Map<String, String> findCodeName() throws StockNotFoundException;


    /**
     * 获取股票的属性
     * @param code 股票代码
     * @return 股票的属性
     */
    public StockAttributesPO getStockAttributes(String code) throws StockNotFoundException;

    /**
     //     * 根据股票名称、时间段查询股票信息列表
     //     * @param beginTime
     //     * @param endTime
     //     * @return
     //     */
//    public StockPO findStockPOByName(String name, LocalDate beginTime, LocalDate endTime) throws StockNotFoundException;
//
//    /**
//     * 根据股票代码、时间段查询股票信息列表
//     * @param code
//     * @param beginTime
//     * @param endTime
//     * @return
//     */
//    public StockPO findStockPOByCode(String code, LocalDate beginTime, LocalDate endTime) throws StockNotFoundException;
}
