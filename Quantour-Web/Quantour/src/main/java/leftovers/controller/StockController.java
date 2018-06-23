package leftovers.controller;

import leftovers.model.StockCurInfo;
import leftovers.model.StockDData;
import leftovers.service.StockService;
import leftovers.util.StockSymbolParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by kevin on 2017/5/10.
 */
@Controller
@RequestMapping(value = "/api/stock", produces = "application/json;charset=UTF-8")
public class StockController {

    @Autowired
    private StockService stockService;

    @RequestMapping(value = "1d_data/{code:\\d+\\.[A-Z]+}", method = RequestMethod.GET)
    public @ResponseBody
    StockDData getStock1DData(@PathVariable("code") String code) {//, @RequestParam LocalDate startDate, @RequestParam LocalDate endDate) {
        return stockService.getStock1DData(code, LocalDate.of(2017, 5, 1), LocalDate.of(2017, 5, 11)).get(0);
    }

    @GetMapping(value = "quote/{code:[A-Z]+\\d+}")
    public @ResponseBody
    String getStockQuoteInfo(@PathVariable("code") String code) {
        return stockService.getStockQuoteInfo(code);
    }

    @GetMapping(value = "chartk/{code:[A-Z]+\\d+}")
    public @ResponseBody
    String getStockK(
            @PathVariable("code") String code,
            @RequestParam("period") String period,
            @RequestParam("type") String type,
            @RequestParam("begin") long begin,
            @RequestParam("end") long end) {
        return stockService.getStockKInfo(code, period, type, begin, end);
    }

    @GetMapping(value = "chartallk/{code:[A-Z]+\\d+}")
    public @ResponseBody
    String getStockAllK(
            @PathVariable("code") String code,
            @RequestParam("period") String period,
            @RequestParam("type") String type){
        return stockService.getStockAllKInfo(code, period, type);
    }

    @GetMapping(value = "chartmin/{code:[A-Z]+\\d+}")
    public @ResponseBody
    String getStockMin(
            @PathVariable("code") String code) {
        return stockService.getStockMinInfo(code);
    }

    @GetMapping(value = "pankou/{code:[A-Z]+\\d+}")
    public @ResponseBody
    String getStockPankou(
            @PathVariable("code") String code) {
        return stockService.getStockPankouInfo(code);
    }

    @GetMapping(value = "industry/{code:[A-Z]+\\d+}")
    public @ResponseBody
    String getStockIndustry(
            @PathVariable("code") String code,
            @RequestParam("size") int size) {
        return stockService.getStockIndustryInfo(code, size);
    }

    @GetMapping(value = "company/{code:[A-Z]+\\d+}")
    public @ResponseBody
    String getStockCompBasic(
            @PathVariable("code") String code) {
        return stockService.getStockCompBasicInfo(code);
    }

    @GetMapping(value = "news/{code:[A-Z]+\\d+}")
    public @ResponseBody
    String getStockNews(
            @PathVariable("code") String code,
            @RequestParam("count") int count,
            @RequestParam("page") int page) {
        return stockService.getStockNewsInfo(code, count, page);
    }

    @GetMapping(value = "announcement/{code:[A-Z]+\\d+}")
    public @ResponseBody
    String getStockAnnouncement(
            @PathVariable("code") String code,
            @RequestParam("count") int count,
            @RequestParam("page") int page) {
        return stockService.getStockAnnouncementInfo(code, count, page);
    }

    @GetMapping(value = "newsSentimentScore/{code:[A-Z]+\\d+}")
    public @ResponseBody
    String getStockSentimentScore(
            @PathVariable("code") String code,
            @RequestParam("beginDate") String beginDate,
            @RequestParam("endDate") String endDate){
        return stockService.getStockNewsSentimentScoreInfo(code, beginDate, endDate);
    }

    @GetMapping(value = "newsAndScore/{code:[A-Z]+\\d+}")
    public @ResponseBody
    String getStockNewsAndScore(
            @PathVariable("code") String code,
            @RequestParam("beginDate") String beginDate,
            @RequestParam("endDate") String endDate){
        return stockService.getStockNewsAndScoreInfo(code, beginDate, endDate);
    }

//    @GetMapping(value = "nextPrice/{code:[A-Z]+\\d+}")
//    public @ResponseBody
//    String predictNextPrice(
//            @PathVariable("code") String code){
//        return stockService.predictNextPrice(StockSymbolParser.toAnotherForm(code));
//    }
//
//    @GetMapping(value = "nextMPrice/{code:[A-Z]+\\d+}")
//    public @ResponseBody
//    String predictNextMPrice(
//            @PathVariable("code") String code,
//            @RequestParam("num") int num){
//        return stockService.predictNextMPrice(StockSymbolParser.toAnotherForm(code), num);
//    }

    @GetMapping(value = "NP1Price/{code:[A-Z]+\\d+}")
    public @ResponseBody
    String getNP1Prices(
            @PathVariable("code") String code,
            @RequestParam("num") int num){

        return stockService.getNP1Price(StockSymbolParser.toAnotherForm(code), num);
    }

}
