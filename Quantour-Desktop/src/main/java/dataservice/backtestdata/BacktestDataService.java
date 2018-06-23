package dataservice.backtestdata;

import util.enums.Benchmark;
import util.enums.Board;
import util.exception.StockHasNoDataException;
import util.exception.StockItemNotFoundException;
import util.exception.StockNotFoundException;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Hiki on 2017/4/2.
 *
 */
public interface BacktestDataService {


    /**
     * 根据股票代码和日期查询股票距离当前日期最后一次的价格
     * 若股票当前日期停牌，则搜索股票最后一次未停牌前的价格，若搜索不到，抛出StockItemNotFoundException
     * @param code
     * @param date
     * @return
     */
    public double getStockLastPriceFromMemory(String code, LocalDate date) throws StockItemNotFoundException;


    /**
     * 根据股票代码和日期查询股票最近n天（包括当天）的价格，如果没有先前的数据，抛出StockItemNotFound异常，提示界面端重新选择日期
     * @param code
     * @param n
     * @return
     */
    public double[] getStockPrice(String code, LocalDate date, int n) throws StockItemNotFoundException;


    /**
     * 根据股票代码列表和日期查询股票列表最近n天（包括当天）的价格，如果当天没有价格，则为前一天的价格
     * @param codes
     * @param date
     * @return
     */
    public Map<String, Double[]> getListStockPrice(List<String> codes, LocalDate date, int n);


    /**
     * 根据板块和日期查询该板块所有股票最近n天（包括当天）的价格
     * 板块指的是主板、创业板之类的，可以在Board类里面根据代码头找到相应的股票列表
     * @param board
     * @param date
     * @return
     */
    public Map<String, Double[]> getBoardStockPrice(Board board, LocalDate date, int n);


    /**
     * 根据行业和日期查询该行业所有股票最近n天（包括当天）的价格
     * @param section 行业，可以在data里面的code-section映射文件中找到相应行业对应的股票
     * @param date
     * @return
     */
    public Map<String, Double[]> getSectionStockPrice(String section, LocalDate date, int n);


    /**
     * 根据股票代码查询股票名称
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
    public List<LocalDate> getTradeDays(LocalDate beginTime, LocalDate endTime);


//    /**
//     * 在TXT文件中查找行业与股票代码的对应关系
//     * @return
//     */
//    public Map<String, String[]> getSectionStocks();

    /**
     * 在TXT文件中查找行业的列表
     * @return
     */
    public List<String> getSections();



//    /**
//     * 在文件夹中查找板块与股票代码的对应关系
//     * @return
//     */
//    public Map<Board, String[]> getBoardStocks();


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
     * 获得所有股票代码
     * @return
     */
    public List<String> getAllStockCodes();



    /**
     * 根据行业查找股票代码列表
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
