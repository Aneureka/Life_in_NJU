package leftovers.controller;

import leftovers.model.BacktestHistory;
import leftovers.service.BacktestService;
import leftovers.util.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by Hiki on 2017/6/9.
 */
@Controller
@RequestMapping(value = "/api/backtest", produces = "application/json;charset=UTF-8")
public class BacktestController {

    @Autowired
    private BacktestService backtestService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public @ResponseBody
    String getBacktestResult(@RequestParam String algoId) throws IOException {
        return backtestService.runAlgorithm(algoId);
    }

    @RequestMapping(value = "/fortest", method = RequestMethod.GET)
    public @ResponseBody
    String getBacktestResultForTest(@RequestParam String algoId) throws IOException {
        return Request.get("http://qaquant.aneureka.cn/api/backtest?algoId=" + algoId);
    }

    @RequestMapping(value = "/history", method = RequestMethod.GET)
    public @ResponseBody
    List<BacktestHistory> getBacktestHistory(@RequestParam String algoId) throws IOException {
        return backtestService.findBacktestHistoryByAlgoId(algoId);
    }

    @RequestMapping(value = "/hisNum", method = RequestMethod.GET)
    public @ResponseBody
    long getBacktestHistoryNum(@RequestParam String algoId) throws IOException {
        return backtestService.findBacktestHistoryNumByAlgoId(algoId);
    }

    @RequestMapping(value = "/last_history", method = RequestMethod.GET)
    public @ResponseBody
    BacktestHistory getLastBacktestHistory(@RequestParam String algoId) throws IOException {
        return backtestService.findLatestBacktestHistoryByAlgoId(algoId);
    }

    @RequestMapping(value = "/downloadResult")
    @ResponseBody
    public ResponseEntity<byte[]> downloadResult(@RequestBody String result){
        try {
            return backtestService.downloadResult(result);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

}
