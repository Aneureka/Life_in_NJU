package dao.backtest;

import datahelper.backtest.BackTestDataHelper;
import datahelper.backtest.BackTestDataHelperImpl;
import dataservice.backtestdata.BacktestDataService;
import util.enums.Benchmark;
import util.enums.Board;
import util.exception.StockHasNoDataException;
import util.exception.StockItemNotFoundException;
import util.exception.StockNotFoundException;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by 97257 on 2017/4/2.
 */
public class BackTestDao implements BacktestDataService{

    private BackTestDataHelper backTestDataHelper;

    public BackTestDao() {
        this.backTestDataHelper = new BackTestDataHelperImpl();
    }


    @Override
    public double getStockLastPriceFromMemory(String code, LocalDate date) throws StockItemNotFoundException {
        try {
            return backTestDataHelper.getStockLastPriceFromMemory(code, date);
        } catch (StockNotFoundException e) {
            e.printStackTrace();
        } catch (StockHasNoDataException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public double[] getStockPrice(String code, LocalDate date, int n) throws StockItemNotFoundException {
        try {
            double[] res = backTestDataHelper.getStockPriceFromMemory(code, date, n);

            for (int i = 0; i < res.length; i++) {
                if (i < res.length - 1 - i){
                    double temp = res[i];
                    res[i] = res[res.length - 1 - i];
                    res[res.length - 1 - i] = temp;
                }
            }

            return res;
        } catch (StockNotFoundException e) {
            e.printStackTrace();
        } catch (StockHasNoDataException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Map<String, Double[]> getListStockPrice(List<String> codes, LocalDate date, int n) {
        Map<String, Double[]> res = new HashMap<>();

        for (String code : codes) {
            try {
                double[] pricesd = getStockPrice(code, date, n);
                Double[] pricesD = new Double[pricesd.length];
                for (int i = 0; i < pricesD.length; i++) {
                    pricesD[i] = new Double(pricesd[i]);
                }
                res.put(code, pricesD);
            } catch (StockItemNotFoundException e) {

            }
        }

        return res;
    }

    @Override
    public Map<String, Double[]> getBoardStockPrice(Board board, LocalDate date, int n){
        List<String> codes = backTestDataHelper.getStockCodesByBoard(board);

        Map<String, Double[]> res = new HashMap<>();

        for (String code : codes) {
            try {
                double[] pricesd = getStockPrice(code, date, n);
                Double[] pricesD = new Double[pricesd.length];
                for (int i = 0; i < pricesD.length; i++) {
                    pricesD[i] = new Double(pricesd[i]);
                }
                res.put(code, pricesD);
            } catch (StockItemNotFoundException e) {

            }
        }

        return res;
    }

    @Override
    public Map<String, Double[]> getSectionStockPrice(String section, LocalDate date, int n) {
        List<String> codes = backTestDataHelper.getStockCodesBySection(section);

        Map<String, Double[]> res = new HashMap<>();

        for (String code : codes) {
            try {
                double[] pricesd = getStockPrice(code, date, n);
                Double[] pricesD = new Double[pricesd.length];
                for (int i = 0; i < pricesD.length; i++) {
                    pricesD[i] = new Double(pricesd[i]);
                }
                res.put(code, pricesD);
            } catch (StockItemNotFoundException e) {

            }
        }

        return res;
    }

    @Override
    public String getStockName(String code) {
        return backTestDataHelper.getStockName(code);
    }

    @Override
    public List<LocalDate> getTradeDays(LocalDate beginTime, LocalDate endTime) {
        return backTestDataHelper.find(beginTime, endTime);
    }


    @Override
    public List<String> getStockCodesBySection(String section) {
        return backTestDataHelper.getStockCodesBySection(section);
    }

    @Override
    public List<String> getStockCodesByUniverseName(String universe) {
        return backTestDataHelper.getStockCodesByUniverseName(universe);
    }

    @Override
    public String getSectionByStockCode(String code) {
        return backTestDataHelper.getSectionByStockCode(code);
    }

    @Override
    public List<String> getStockCodesByBoard(Board board) {
        return backTestDataHelper.getStockCodesByBoard(board);
    }

    @Override
    public TreeMap<LocalDate, Double> getPricesByBenchmark(Benchmark benchmark, LocalDate beginTime, LocalDate endTime) {
        return backTestDataHelper.getPricesByBenchmark(benchmark, beginTime, endTime);
    }

    @Override
    public TreeMap<LocalDate, Double> getAveragePricesByCodeList(List<String> codes, LocalDate beginTime, LocalDate endTime) {
        return backTestDataHelper.getAveragePricesByCodeList(codes, beginTime, endTime);
    }

    @Override
    public List<String> getAllStockCodes() {
        return backTestDataHelper.getAllStockCodes();
    }


    @Override
    public List<String> getSections() {
        return backTestDataHelper.getSections();
    }
}
