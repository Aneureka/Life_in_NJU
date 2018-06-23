package datahelper.backtest;

import po.stock.StockPO;
import util.enums.Benchmark;
import util.enums.Board;
import util.exception.StockHasNoDataException;
import util.exception.StockItemNotFoundException;
import util.exception.StockNotFoundException;
import vo.backtest.PriceVO;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by 97257 on 2017/4/2.
 */
public interface BackTestDataHelper {


    /**
     * 所有的Price均指复权收盘价
     * 对于没有数据的股票，应先剔除（不加入到Map中），对于单只股票的情况，抛出StockItemNotFoundException
     * 对于多只股票的情况，应将没有数据的股票剔除，如果是调用单只股票的方法，应该先处理掉单只股票抛出的异常
     * 获取到最近几天的股票时，因为csv文件中是按日期倒序排列，记得先转换成正序，可以用Collections.reverse()方法
     * 其中，获取最近n天的价格的指的是交易日，既不是实际日期，!!!也不是股票最近的几个数据!!!
     */


    /**
     * 根据股票代码和日期查询股票距离当前日期最后一次的价格
     * 若股票当前日期停牌，则搜索股票最后一次未停牌前的价格，若搜索不到，抛出StockItemNotFoundException
     * @param code
     * @param date
     * @return
     */
    public double getStockLastPriceFromMemory(String code, LocalDate date) throws StockItemNotFoundException, StockNotFoundException, StockHasNoDataException;


    /**
     * 根据股票代码和日期查询股票最近n天（包括当天）的价格，如果没有先前的数据，抛出StockItemNotFound异常，提示界面端重新选择日期
     * @param code
     * @param n
     * @return
     */
    public double[] getStockPriceFromMemory(String code, LocalDate date, int n) throws StockItemNotFoundException, StockNotFoundException, StockHasNoDataException;


    /**
     * 根据股票代码查询股票名称，在stockdatahelper中已经由方法了
     * @param code
     * @return
     */
    public String getStockName(String code);

    /**
     * 根据起始日期和结束日期返回中间的交易日列表（正序）
     * @param beginTime
     * @param endTime
     * @return
     */
    public List<LocalDate> find(LocalDate beginTime, LocalDate endTime);


    /**
     * 根据板块查找股票代码列表
     * @param board
     * @return
     */
    public List<String> getStockCodesByBoard(Board board);


    /**
     * 根据基准查找一段时间（交易日）内基准的价格指数
     * @param benchmark
     * @param beginTime
     * @param endTime
     * @return
     */
    public TreeMap<LocalDate, Double> getPricesByBenchmark(Benchmark benchmark, LocalDate beginTime, LocalDate endTime);


    /**
     * 根据股票代码列表查找一段时间（交易日）内它们的平均价格（注意停牌股票）
     * @param codes
     * @param beginTime
     * @param endTime
     * @return
     */
    public TreeMap<LocalDate, Double> getAveragePricesByCodeList(List<String> codes, LocalDate beginTime, LocalDate endTime);


    /**
     * 在TXT文件中查找行业的列表
     * @return
     */
    public List<String> getSections();


    /**
     * 获得所有股票代码
     * @return
     */
    public List<String> getAllStockCodes();


    /**
     * 根据行业查找股票代码
     * @param section
     * @return
     */
    public List<String> getStockCodesBySection(String section);


    /**
     * 根据自定义的股票池名称查找股票代码
     * @param universe
     * @return
     */
    public List<String> getStockCodesByUniverseName(String universe);


    /**
     * 根据股票代码查询股票行业
     * @param code
     * @return
     */
    public String getSectionByStockCode(String code);

}
