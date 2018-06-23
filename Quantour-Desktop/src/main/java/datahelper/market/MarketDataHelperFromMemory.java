package datahelper.market;

import datahelper.utilities.DataMirror;
import po.market.MarketItemPO;
import po.stock.StockItemPO;
import util.exception.StockHasNoDataException;
import util.exception.StockNotFoundException;
import util.exception.TimeException;
import datahelper.utilities.FileOpener;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

/**
 * Created by kevin on 2017/4/1.
 */
public class MarketDataHelperFromMemory implements MarketDataHelper {

    private DataMirror dataMirror;

    public MarketDataHelperFromMemory() {
        try {
            dataMirror = DataMirror.getInstance();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取市场的全部股票代码
     *
     * @return 市场的全部股票代码 ，无为空表
     */
    @Override
    public List<String> findAllStockCodes() {
        List<String> res = new ArrayList<>();

        try {
            //打开股票代码文件
            BufferedReader reader = new FileOpener().openCodesFileAsBR();

            //提取所有股票代码
            String line;
            while ((line = reader.readLine()) != null) {
                res.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return res;
    }

    /**
     * 获取某只股票的最近交易日条目
     *
     * @param code 股票代码
     * @return 某只股票的最近交易日条目 ，无为null
     * @throws StockNotFoundException  股票不存在
     * @throws StockHasNoDataException 股票无数据
     */
    @Override
    public MarketItemPO find(String code) throws StockNotFoundException, StockHasNoDataException {
        String[] stock = dataMirror.getStocks().get(code);

        if (stock == null)
            throw new StockNotFoundException("Stcok " + code + " not found.");

        if (stock.length <= 1)
            throw new StockHasNoDataException("Stcok " + code + " has no data.");

        StockItemPO current = StockItemPO.parse(stock[1]);
        StockItemPO previous = stock.length >= 3 ? StockItemPO.parse(stock[2]) : null;

        return new MarketItemPO(current, previous);
    }

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
    @Override
    public MarketItemPO find(String code, LocalDate date) throws StockNotFoundException, StockHasNoDataException, TimeException {
        String[] stock = dataMirror.getStocks().get(code);

        if (stock == null)
            throw new StockNotFoundException("Stcok " + code + " not found.");

        if (stock.length <= 1)
            throw new StockHasNoDataException("Stcok " + code + " has no data.");

        int index = searchByDate(stock, date);
        if (index == -1)
            throw new TimeException("Stcok " + code + " has no data on date " + date + ".");

        StockItemPO current = StockItemPO.parse(stock[index]);
        StockItemPO previous = stock.length >= index + 2 ? StockItemPO.parse(stock[index + 1]) : null;

        return new MarketItemPO(current, previous);
    }

    /**
     * 获得某天的股票信息列表（用于分析当日股票市场的行情）
     *
     * @param date 某一天
     * @return date当天的股票条目 ，没有返回空表
     */
    @Override
    public List<MarketItemPO> find(LocalDate date) {
        List<MarketItemPO> res = new ArrayList<>();

        Set<String> codes = dataMirror.getStocks().keySet();
        for (String code: codes) {
            try {
                res.add(find(code, date));
            } catch (StockNotFoundException e) {

            } catch (StockHasNoDataException e) {

            } catch (TimeException e) {

            }
        }

        return res;
    }

    /**
     * 从一只股票的日线数据中找出某一天的数据的索引，要求股票的日线数据是已经按照日期先后排好序的
     *
     * @param stock 某只股票的日线数据
     * @param date 日期
     * @return 一只股票某一天的日线数据的索引，找不到则返回-1
     */
    private int searchByDate(String[] stock, LocalDate date){
        //若日期不在搜索范围内，返回-1
        if (date.isBefore(StockItemPO.parse(stock[stock.length - 1]).getDate()) || date.isAfter(StockItemPO.parse(stock[1]).getDate())) {
            return -1;
        }

        //初始化搜索区间及中点
        int start = 1;
        int end = stock.length - 1;
        int middle = (start + end) / 2;

        //不断判断中点，缩小搜索区间，找到则终止
        while (end - start >= 0) {
            LocalDate candidateDate = StockItemPO.parse(stock[middle]).getDate();
            if (date.isEqual(candidateDate)) {
                return middle;
            } else if (date.isBefore(candidateDate)) {
                start = middle + 1;
            } else {
                end = middle - 1;
            }
            middle = (start + end) / 2;
        }

        //搜索不到
        return -1;
    }


}
