package datahelper.utilities;

import util.exception.StockNotFoundException;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by kevin on 2017/4/1.
 */
public class DataMirror {

    private Map<String, String[]> stocks;

    private String[] tradeDays;

    private String[][] stockAttributes;

    private static DataMirror instance;

    private DataMirror(){
        try {
            //初始化stocks
            stocks = new HashMap<>();

            //获取所有股票代码
            String[] codes = new FileOpener().openCodesFileAsBR().lines().toArray(String[]::new);

            //获取所有股票日线数据
            for (String code : codes) {
                stocks.put(code, new FileOpener().openStockFileAsBR(code).lines().toArray(String[]::new));
            }

            //初始化tradeDays
            tradeDays = new FileOpener().openTradeDayFileAsBR().lines().toArray(String[]::new);

            //初始化stockAttributes
            String[] lines = new FileOpener().openStockAttributeFileAsBR().lines().toArray(String[]::new);
            stockAttributes = new String[lines.length][];
            for (int i = 0; i < lines.length; i++) {
                stockAttributes[i] = lines[i].split("\t");
            }

            System.out.println("DataMirror Construt.");
            //初始化instance
            instance = null;
        } catch (StockNotFoundException e) {
            System.out.println("File codes.txt does not match folder stocks.");
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            System.out.println("File codes.txt missed.");
            e.printStackTrace();
        }
    }

    public static DataMirror getInstance() throws IOException {
        if (instance == null)
            instance = new DataMirror();
        return instance;
    }

    public Map<String, String[]> getStocks() {
        return stocks;
    }

    public String[] getTradeDays() {
        return tradeDays;
    }

    public String[][] getStockAttributes(){
        return stockAttributes;
    }

}
