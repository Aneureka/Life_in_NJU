package datahelper.backtest;

import datahelper.utilities.DataFrame;
import datahelper.utilities.DataMirror;
import datahelper.utilities.FileOpener;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import po.stock.StockItemPO;
import util.enums.Benchmark;
import util.enums.Board;
import util.exception.StockHasNoDataException;
import util.exception.StockItemNotFoundException;
import util.exception.StockNotFoundException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Array;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by 97257 on 2017/4/2.
 */
public class BackTestDataHelperImpl implements BackTestDataHelper {

    private DataMirror dataMirror;

    private FileOpener fileOpener;

    /**
     * 构造函数
     *
     * @throws IOException
     */
    public BackTestDataHelperImpl() {
        try {
            dataMirror = DataMirror.getInstance();
        } catch (IOException e) {
            e.printStackTrace();
        }
        fileOpener = new FileOpener();
    }

    @Override
    public double getStockLastPriceFromMemory(String code, LocalDate date) throws StockItemNotFoundException, StockNotFoundException, StockHasNoDataException {
        return find(code, date, false).getAdjClose();
    }

    @Override
    public double[] getStockPriceFromMemory(String code, LocalDate date, int n) throws StockItemNotFoundException, StockNotFoundException, StockHasNoDataException {
        List<StockItemPO> stockItemPOList = find(code, date, n);

        double[] res = new double[stockItemPOList.size()];
        for (int i = 0; i < res.length; i++) {
            res[i] = stockItemPOList.get(i).getAdjClose();
        }

        return res;
    }

    @Override
    public String getStockName(String code) {
        String[][] stockAttributes = dataMirror.getStockAttributes();

        for (String[] stockAttribute : stockAttributes) {
            if (stockAttribute[0].equals(code)) {
                return stockAttribute[1];
            }
        }

        return null;
    }

    @Override
    public List<LocalDate> find(LocalDate beginTime, LocalDate endTime) {
        try {
            return fileOpener.openTradeDayFileAsBR().lines()
                    .map(LocalDate::parse)
                    .filter(date -> (date.isAfter(beginTime) || date.isEqual(beginTime)) && (date.isBefore(endTime) || date.isEqual(endTime)))
                    .sorted()
                    .collect(Collectors.toList());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<String> getStockCodesBySection(String section) {
        return fileOpener.openStockAttributeFileAsBR().lines()
                .skip(1)
                .filter(line -> line.split("\t")[2].equals(section))
                .map(line -> line.split("\t")[0])
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getStockCodesByUniverseName(String universe) {
        return fileOpener.openUniverseFileAsBR(universe).lines().collect(Collectors.toList());
    }

    @Override
    public String getSectionByStockCode(String code) {
        try {
            BufferedReader bufferedReader = fileOpener.openStockAttributeFileAsBR();

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.split("\t")[0].equals(code)) {
                    return line.split("\t")[2];
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<String> getStockCodesByBoard(Board board) {
        return fileOpener.openStockAttributeFileAsBR().lines()
                .skip(1)
                .map(line -> line.split("\t")[0])
                .filter(line -> Board.getBoardByStock(line) == board)
                .collect(Collectors.toList());
    }

    @Override
    public TreeMap<LocalDate, Double> getPricesByBenchmark(Benchmark benchmark, LocalDate beginTime, LocalDate endTime) {
        List<String> lines = fileOpener.openBenchmarkFileAsBR(benchmark.toString()).lines()
                .filter(p -> (LocalDate.parse(p.split("\t")[0]).isAfter(beginTime) || LocalDate.parse(p.split("\t")[0]).isEqual(beginTime)) && (LocalDate.parse(p.split("\t")[0]).isBefore(endTime) || LocalDate.parse(p.split("\t")[0]).isEqual(endTime)))
                .collect(Collectors.toList());

        TreeMap<LocalDate, Double> res = new TreeMap<>();
        for (String line : lines) {
            res.put(LocalDate.parse(line.split("\t")[0]), Double.parseDouble(line.split("\t")[1]));
        }
        return res;
    }

    @Override
    public TreeMap<LocalDate, Double> getAveragePricesByCodeList(List<String> codes, LocalDate beginTime, LocalDate endTime) {
        TreeMap<LocalDate, Double> res = new TreeMap<>();

        List<LocalDate> dates = find(beginTime, endTime);

        for (LocalDate date : dates) {
            List<Double> prices = new ArrayList<>();
            for (String code : codes) {
                try {
                    prices.add(find(code, date, true).getAdjClose());
                } catch (StockNotFoundException e) {
                } catch (StockHasNoDataException e) {
                } catch (StockItemNotFoundException e) {
                }
            }
            res.put(date, prices.stream().mapToDouble(Double::new).average().orElse(0));
        }

        return res;
    }

    @Override
    public List<String> getSections() {
        return fileOpener.openStockAttributeFileAsBR().lines()
                .skip(1)
                .map(line -> line.split("\t")[2])
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getAllStockCodes() {
        List<String> res = new ArrayList<>();

        try {
            //打开股票代码文件
            BufferedReader reader = new FileOpener().openCodesFileAsBR();

            //提取所有股票代码
            res = reader.lines().collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return res;
    }


    private int accurateSearchByDate(String[] stock, LocalDate date) {
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

    /**
     * 从一只股票的日线数据中找出某一天或日期最靠近的前一天的数据的索引，要求股票的日线数据是已经按照日期先后排好序的
     *
     * @param stock 某只股票的日线数据
     * @param date  日期
     * @return 一只股票某一天或日期最靠近的前一天的日线数据的索引，找不到则返回-1
     */
    private int inaccurateSearchByDate(String[] stock, LocalDate date) {
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

        //搜索不到准确的就搜索最靠近的前一天
        int[] candidates = new int[3];
        candidates[0] = Math.min(start, end) - 1;
        candidates[1] = start;
        candidates[2] = end;
        LocalDate closestDate = LocalDate.MIN;
        int res = -1;
        for (int i = 0; i < candidates.length; i++) {
            if (0 < candidates[i] && candidates[i] < stock.length) {
                LocalDate candidateDate = StockItemPO.parse(stock[candidates[i]]).getDate();
                if (candidateDate.isBefore(date) && candidateDate.isAfter(closestDate)) {
                    closestDate = candidateDate;
                    res = candidates[i];
                }
            }
        }
        if (res != -1)
            return res;

        //搜索不到
        return -1;
    }

    private List<LocalDate> getPreviousTradeDays(LocalDate date, int n) throws NotFound {
        String[] tradedays = dataMirror.getTradeDays();

        int index = searchTradeDay(tradedays, date);

        if (index == -1)
            throw new NotFound();

        List<LocalDate> res = new ArrayList<>();
        try {
            for (int i = index; i < index + n; i++) {
                res.add(LocalDate.parse(tradedays[i]));
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new NotFound();
        }

        return res.stream().sorted().collect(Collectors.toList());
    }

    private int searchTradeDay(String[] tradeDays, LocalDate date) {
        //若日期不在搜索范围内，返回-1
        if (date.isBefore(LocalDate.parse(tradeDays[tradeDays.length - 1])) || date.isAfter(LocalDate.parse(tradeDays[0]))) {
            return -1;
        }

        //初始化搜索区间及中点
        int start = 0;
        int end = tradeDays.length - 1;
        int middle = (start + end) / 2;

        //不断判断中点，缩小搜索区间，找到则终止
        while (end - start >= 0) {
            LocalDate candidateDate = LocalDate.parse(tradeDays[middle]);
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

    public StockItemPO find(String code, LocalDate date, boolean accurateSearch) throws StockNotFoundException, StockHasNoDataException, StockItemNotFoundException {
        String[] stock = dataMirror.getStocks().get(code);

        if (stock == null)
            throw new StockNotFoundException("Stcok " + code + " not found.");

        if (stock.length <= 1)
            throw new StockHasNoDataException("Stcok " + code + " has no data.");

        int index = accurateSearch ? accurateSearchByDate(stock, date) : inaccurateSearchByDate(stock, date);
        if (index == -1)
            throw new StockItemNotFoundException("Stcok " + code + " has no data on date " + date + " or its previous closest date.");

        return StockItemPO.parse(stock[index]);
    }

    public List<StockItemPO> find(String code, LocalDate date, int n) throws StockHasNoDataException, StockNotFoundException, StockItemNotFoundException {
        try {
            List<LocalDate> tradeDays = getPreviousTradeDays(date, n);

            List<StockItemPO> res = new ArrayList<>();
            for (LocalDate tradeDay : tradeDays) {
                res.add(find(code, tradeDay, true));
            }

            return res;
        } catch (NotFound notFound) {
            throw new StockItemNotFoundException("Trade days lack.");
        }
    }




/*-----------------------------------------下面内容如果用不到就删了---------------------------------------------*/

//    /**
//     * 根据股票代码查询股票
//     * @param code
//     * @return
//     */
//    private StockPO getStockPOFromMemory(String code){
//
//        // 取得原始数据
//        String[] rawStock = rawStocks.get(code);
//
//        // 构造StockItemPOList
//        List<StockItemPO> stockItemPOs = new ArrayList<>();
//        // 去掉i=0，即头部的信息
//        for (int i = 1; i < rawStock.length; i++)
//            stockItemPOs.add(StockDataHelperImpl.toStockItemPO(rawStock[i]));
//        return new StockPO(stockItemPOs);
//    }


//    /**
//     * 根据股票代码及日期查询股票当天信息
//     * @param code
//     * @param date
//     * @return
//     */
//    private StockItemPO getStockItemPOFromMemory(String code, LocalDate date){
//        return null;
//    }


    // 将所有的股票存储结构化并存储起来
//    public void initStocks(){
//        System.out.println(LocalDateTime.now());
//        Iterator<Map.Entry<String, String[]>> entries = rawStocks.entrySet().iterator();
//        while (entries.hasNext()){
//            Map.Entry<String, String[]> entry = entries.next();
//            // 对String[]进行操作
//            List<StockItemPO> stockItemPOs = new ArrayList<>();
//            for (int i = 0; i < entry.getValue().length-1; i++)
//                stockItemPOs.add(StockDataHelperImpl.toStockItemPO(entry.getValue()[i+1]));
//
//            stocks.put(entry.getKey(), stockItemPOs);
//        }
//
//        System.out.println(LocalDateTime.now());
//
//    }
}

