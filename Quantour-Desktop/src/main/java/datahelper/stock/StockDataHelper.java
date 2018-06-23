package datahelper.stock;

import po.stock.StockAttributesPO;
import util.exception.StockNotFoundException;
import po.stock.StockPO;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * Created by kevin on 2017/3/6.
 */
public interface StockDataHelper {




    /**
     * 在csv文件中根据股票代码查询股票信息列表
     * @param code
     * @return
     * @throws StockNotFoundException
     */
    public StockPO findStockPOByCodeFromCsv(String code) throws StockNotFoundException;


    /**
     * 在csv文件中根据股票名称查询股票信息列表
     * @param name
     * @return
     * @throws StockNotFoundException
     */
    public StockPO findStockPOByNameFromCsv(String name) throws StockNotFoundException;

    /**
     * 在code-name关系映射文件中通过name寻找code
     * @param name
     * @return
     * @throws IOException
     */
    public String findCodeByNameFromTxt(String name) throws StockNotFoundException;


    /**
     * 在code-name关系映射文件中查找所有code-name映射关系
     * @return
     * @throws StockNotFoundException
     */
    public Map<String, String> findCodeNameFromTxt() throws StockNotFoundException;


    /**
     * 获取股票的属性
     * @param code 股票代码
     * @return 股票的属性
     */
    public StockAttributesPO getStockAttributes(String code) throws StockNotFoundException;

//    /**
//     * 在csv文件中根据股票代码、时间段查询股票信息列表
//     * @param code
//     * @param beginTime
//     * @param endTime
//     * @return
//     */
//    public StockPO findStockPOByCodeFromCsv(String code, LocalDate beginTime, LocalDate endTime) throws StockNotFoundException;


//    /**
//     * 在csv文件中根据股票名称、时间段查询股票信息列表
//     * @param beginTime
//     * @param endTime
//     * @return
//     */
//    public StockPO findStockPOByNameFromCsv(String name, LocalDate beginTime, LocalDate endTime) throws StockNotFoundException;

}
