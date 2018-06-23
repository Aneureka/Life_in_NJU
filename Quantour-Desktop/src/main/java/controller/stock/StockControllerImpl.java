package controller.stock;

import bl.stock.Stock;
import blservice.stockblservice.StockBLService;
import util.exception.StockNotFoundException;
import util.exception.TimeException;
import util.tool.TimeFormatter;
import vo.stock.StockItemVO;
import vo.stock.StockVO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by kevin on 2017/3/6.
 */
public class StockControllerImpl implements StockController{

    StockBLService stockBLService;

    public StockControllerImpl() {
        this.stockBLService = new Stock();
    }

    @Override
    public StockVO findStock(String index) throws TimeException, StockNotFoundException {
        return isCode(index) ? stockBLService.findStockByCode(index) : stockBLService.findStockByName(index);
    }

    @Override
    public StockVO findStock(String index, LocalDate beginTime, LocalDate endTime) throws StockNotFoundException, TimeException {
        return isCode(index) ? stockBLService.findStockByCode(index, beginTime, endTime) : stockBLService.findStockByName(index, beginTime, endTime);
    }

    @Override
    public StockVO findMonthStock(String index, LocalDate beginTime, LocalDate endTime) throws TimeException, StockNotFoundException {
        return isCode(index) ? stockBLService.findMonthStockByCode(index, beginTime, endTime) : stockBLService.findMonthStockByName(index, beginTime, endTime);
    }

    @Override
    public StockVO findWeekStock(String index, LocalDate beginTime, LocalDate endTime) throws TimeException, StockNotFoundException {
        return isCode(index) ? stockBLService.findWeekStockByCode(index, beginTime, endTime) : stockBLService.findWeekStockByName(index, beginTime, endTime);
    }

    /**
     * 通过查询获得建议的股票代码和名称列表，其中代码优先级高
     *
     * @param query
     * @return
     * @throws StockNotFoundException
     */
    @Override
    public List<String> findSuggestions(String query) throws StockNotFoundException {
        return stockBLService.findSuggestions(query);
    }

    @Override
    public List<String> getSections() {
        return stockBLService.getSections();
    }

    @Override
    public List<String> getStockCodesBySection(String sectionName) {
        return stockBLService.getStockCodesBySection(sectionName);
    }

    @Override
    public String getSectionByStockCode(String code) {
        return stockBLService.getSectionByStockCode(code);
    }

    /**
     * 判断输入的是股票代码还是股票名称
     * @param str
     * @return
     */
    private boolean isCode(String str) {
        Pattern p = Pattern.compile("\\d+");
        return p.matcher(str).matches();

    }


}
