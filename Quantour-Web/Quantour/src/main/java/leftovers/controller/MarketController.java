package leftovers.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONReader;
import leftovers.model.StockCurInfo;
import leftovers.service.MarketService;
import leftovers.util.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Hiki on 2017/5/17.
 */

@Controller
@RequestMapping(value = "/api/market", produces = "application/json;charset=UTF-8")
public class MarketController {

    @Autowired
    private MarketService marketService;

    @GetMapping(value = "/hot_rank")
    public @ResponseBody String getHotRank(
            @RequestParam("size") int size){
        return marketService.getHotRankInfo(size);
    }

    @GetMapping(value = "/longhubang")
    public @ResponseBody String getLonghubang(
            @RequestParam("date") String date){
        return marketService.getLongHuBangInfo(date);
    }

    @GetMapping(value = "/rank")
    public @ResponseBody String getRank(
            @RequestParam("size") int size,
            @RequestParam("order") String order,
            @RequestParam("exchange") String exchange,
            @RequestParam("stockType") String stockType,
            @RequestParam("orderBy") String orderBy){
        return marketService.getRankInfo(size, order, exchange, stockType, orderBy);
    }

    @GetMapping(value = "/news")
    public @ResponseBody String getNews(
            @RequestParam int page){
        return marketService.getNewsInfo(page);
    }

    @RequestMapping(value = "/stock_list", method = RequestMethod.GET)
    public @ResponseBody
    List<StockCurInfo> getMarketSnapshot(@RequestParam String board, @SortDefault(sort = "changePercent", direction = Sort.Direction.DESC) Sort sort){
        Map<String, String> map = new HashMap<>();
        map.put("a", "A股");
        map.put("sha", "上证A股");
        map.put("sza", "深证A股");
        map.put("zxb", "中小企业板");
        map.put("cyb", "创业板");

        return marketService.getStockList(map.get(board), sort);
    }

    @RequestMapping(value = "/stock_list_page", method = RequestMethod.GET)
    public @ResponseBody
    Page<StockCurInfo> getMarketSnapshotByPageable(@RequestParam String board, @PageableDefault(value = 10) Pageable pageable){
        Map<String, String> map = new HashMap<>();
        map.put("a", "A股");
        map.put("sha", "上证A股");
        map.put("sza", "深证A股");
        map.put("zxb", "中小企业板");
        map.put("cyb", "创业板");

        return marketService.getStockListByPageable(map.get(board), pageable.previousOrFirst());
    }

    @RequestMapping(value = "/stocklist_page", method = RequestMethod.GET)
    public @ResponseBody
    String getStockList(HttpServletRequest request){
        try {
            return marketService.getStockListByBoard(request.getParameterMap());
        } catch (IOException e) {
            e.printStackTrace();
            return JSONResult.fillResultString(500, "获取股票列表失败", null);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return JSONResult.fillResultString(500, "获取股票列表失败", null);
        }
    }
}
