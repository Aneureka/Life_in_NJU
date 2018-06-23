package datahelper.market;

import datahelper.utilities.FileChecker;
import po.market.MarketItemPO;
import po.stock.StockItemPO;
import util.exception.StockHasNoDataException;
import util.exception.StockNotFoundException;
import util.exception.TimeException;
import datahelper.utilities.FileOpener;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Hiki on 2017/3/8.
 */
public class MarketDataHelperFromFile implements MarketDataHelper {

    private static int lineLength = 200;

    private FileOpener fileOpener = new FileOpener();

    /**
     * 在csv文件内获得某天的股票信息列表（用于分析当日股票市场的行情）
     *
     * @param date 某一天
     * @return date当天的股票条目，没有返回空表
     * @throws IOException IO异常
     */
    public List<StockItemPO> findStockItemsByTimeFromCsv(LocalDate date) throws IOException {
        List<StockItemPO> res = new ArrayList<>();

        //连接到文件
        File file = new File(System.getProperty("user.dir") + "\\data\\股票历史数据ALL.csv");
        BufferedReader reader = new BufferedReader(new FileReader(file));

        //读取表头
        String line = reader.readLine();

        //读取股票数据
        StockItemPO stockItemPO;
        while ((line = reader.readLine()) != null) {
            stockItemPO = StockItemPO.parse(line);

            //判断读取的股票条目是否为date
            if (stockItemPO.getDate().isEqual(date)) {
                res.add(stockItemPO);
            }
        }

        return res;
    }

    /**
     * 在csv文件内根据股票代码和序列号或取股票条目
     *
     * @param code   股票代码
     * @param serial 序列号
     * @return 股票条目，无为null
     * @throws IOException IO异常
     */
    public StockItemPO findSIBySerialAndCodeFromCsv(String code, long serial) throws IOException {
        StockItemPO res = null;

        //连接到文件
        File file = new File(System.getProperty("user.dir") + "\\data\\stocks\\" + code + ".csv");
        BufferedReader reader = new BufferedReader(new FileReader(file));

        //读取表头
        String line = reader.readLine();

        //读取股票数据
        StockItemPO stockItemPO;
        while ((line = reader.readLine()) != null) {
            stockItemPO = StockItemPO.parse(line);

            //判断读取的股票条目是否为serial
            if (stockItemPO.getSerial() == serial) {
                res = stockItemPO;
                break;
            }
        }

        return res;
    }

    /**
     * 获取某只股票的最近交易日条目
     *
     * @param code 股票代码
     * @return 某只股票的最近交易日条目，无为null
     */
    public StockItemPO findLatestStockItemByCode(String code) throws IOException {
        StockItemPO res = null;

        //连接到文件
        File file = new File(System.getProperty("user.dir") + "\\data\\stocks\\" + code + ".csv");
        BufferedReader reader = new BufferedReader(new FileReader(file));

        //读取表头
        String line = reader.readLine();

        //读取最近交易日条目
        if ((line = reader.readLine()) != null)
            res = StockItemPO.parse(line);

        return res;
    }

    /**
     * 获取市场的全部股票代码
     *
     * @return 市场的全部股票代码, 无为空表
     */
    public List<String> findAllStockCodes() {
        List<String> res = new ArrayList<>();

        try {
            //连接到股票代码文件
            BufferedReader reader = fileOpener.openCodesFileAsBR();

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
    public MarketItemPO find(String code) throws StockNotFoundException, StockHasNoDataException {
        //打开文件
        BufferedReader reader = fileOpener.openStockFileAsBR(code);

        //检查文件
        FileChecker.checkStockFile(reader);

        //读取表头
        try {
            reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //读取最近交易日条目
        return readLineToMIP(reader);
    }

    /**
     * 获取某只股票的某日条目
     *
     * @param code 股票代码
     * @param date 某天
     * @return 某只股票的某日条目
     * @throws StockNotFoundException  股票不存在
     * @throws StockHasNoDataException 股票无数据
     * @throws TimeException           输入的日期不是该股票的交易日
     */
    public MarketItemPO find(String code, LocalDate date) throws StockNotFoundException, StockHasNoDataException, TimeException {
        //打开文件
        BufferedReader reader = fileOpener.openStockFileAsBR(code);

        //检查文件
        FileChecker.checkStockFile(reader);

        //读取表头
        try {
            reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //读取某交易日条目
        MarketItemPO res = null;
        StockItemPO stockItemPO;
        while ((stockItemPO = readLineToSIP(reader)) != null) {
            if (stockItemPO.getDate().isEqual(date)) {
                res = new MarketItemPO(stockItemPO, readLineToSIP(reader));
                break;
            }
        }

        //日期为非交易日的情况
        if (res == null)
            throw new TimeException();

        return res;
    }


    /**
     * 在csv文件内获得某天的股票信息列表（用于分析当日股票市场的行情）
     *
     * @param date 某一天
     * @return date当天的股票条目 ，没有返回空表
     */
    @Override
    public List<MarketItemPO> find(LocalDate date) {
        List<String> codes = findAllStockCodes();
        List<MarketItemPO> res = new CopyOnWriteArrayList<MarketItemPO>();

        for (String code : codes) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        res.add(find(code, date));
                    } catch (StockNotFoundException e) {

                    } catch (StockHasNoDataException e) {

                    } catch (TimeException e) {

                    }
                }
            });

            thread.start();
        }

        return res;
    }

    private StockItemPO readLineToSIP(BufferedReader reader) {
        String line = null;
        try {
            line = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return line == null ? null : StockItemPO.parse(line);
    }

    private MarketItemPO readLineToMIP(BufferedReader reader) {
        try {
            MarketItemPO res;

            StockItemPO current = readLineToSIP(reader);
            reader.mark(lineLength);
            StockItemPO previous = readLineToSIP(reader);
            reader.reset();

            res = current == null ? null : new MarketItemPO(current, previous);

            return res;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}


