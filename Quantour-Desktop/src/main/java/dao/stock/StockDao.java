package dao.stock;

import datahelper.stock.StockDataHelper;
import datahelper.stock.StockDataHelperImpl;
import dataservice.stock.StockDataService;
import po.stock.StockAttributesPO;
import util.exception.StockNotFoundException;
import po.stock.StockPO;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Map;

/**
 * Created by kevin on 2017/3/6.
 */
public class StockDao implements StockDataService {

    StockDataHelper stockDataHelper;

    public StockDao() {
        this.stockDataHelper = new StockDataHelperImpl();
    }


//    @Override
//    public StockPO findStockPOByName(String name) throws StockNotFoundException {
//        return stockDataHelper.findStockPOByNameFromCsv(name);
//    }


    @Override
    public StockPO findStockPOByCode(String code) throws StockNotFoundException{
        return stockDataHelper.findStockPOByCodeFromCsv(code);
    }


    @Override
    public String findCodeByName(String name) throws StockNotFoundException {
        return stockDataHelper.findCodeByNameFromTxt(name);
    }

    /**
     * 查找code-name映射关系
     * @return
     * @throws StockNotFoundException
     */
    @Override
    public Map<String, String> findCodeName() throws StockNotFoundException {
        return stockDataHelper.findCodeNameFromTxt();
    }

    @Override
    public StockAttributesPO getStockAttributes(String code) throws StockNotFoundException {
        return stockDataHelper.getStockAttributes(code);
    }

//    @Override
//    public StockPO findStockPOByName(String name, LocalDate beginTime, LocalDate endTime) throws StockNotFoundException {
//        return stockDataHelper.findStockPOByNameFromCsv(name, beginTime, endTime);
//    }
//
//
//    @Override
//    public StockPO findStockPOByCode(String code, LocalDate beginTime, LocalDate endTime) throws StockNotFoundException{
//        return stockDataHelper.findStockPOByCodeFromCsv(code, beginTime, endTime);
//    }
}
