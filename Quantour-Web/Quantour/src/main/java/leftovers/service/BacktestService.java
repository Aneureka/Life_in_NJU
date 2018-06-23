package leftovers.service;

import com.alibaba.fastjson.JSON;
import leftovers.model.Algorithm;
import leftovers.model.BacktestHistory;
import leftovers.model.backtest.Summary;
import leftovers.repository.AlgorithmRepository;
import leftovers.repository.BacktestHistoryRepository;
import leftovers.service.AlgorithmService;
import leftovers.service.backtestserviceimpl.Analyser;
import leftovers.service.backtestserviceimpl.BacktestHelper;
import leftovers.service.backtestserviceimpl.ResultReader;
import leftovers.util.JsonFormatTool;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.jws.Oneway;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Hiki on 2017/6/8.
 */
@Service
public class BacktestService {


    @Autowired
    private BacktestHelper backtestHelper;

    @Autowired
    private ResultReader resultReader;

    @Autowired
    private Analyser analyser;

    @Autowired
    private AlgorithmService algorithmService;

    @Autowired
    private AlgorithmRepository algorithmRepository;

    @Autowired
    private BacktestHistoryRepository backtestHistoryRepository;

    private final static String DEFAULT_RESULT = new org.json.JSONObject().put("retCode", 0).toString();

    public BacktestService() {
    }

    public String runAlgorithm(String algoId) {

        Algorithm algorithm = algorithmService.findAlgorithmById(algoId);

        // 回测
        if (!backtestHelper.backtest(algorithm.getAlgoId(), algorithm.getUsername(), algorithm.getCode(), algorithm.getBeginDate(), algorithm.getEndDate(), algorithm.getStockStartCash(), algorithm.getBenchmark())) {
            createFailedBacktestHistory(algorithm);
            return DEFAULT_RESULT;
        }

        // 读取结果
        if (!resultReader.readResults(backtestHelper.getResultPath())) {
            createFailedBacktestHistory(algorithm);
            return DEFAULT_RESULT;
        }

        // 进行分析
        String result = analyser.analyse(resultReader.getResults());
        createSuccessfulBacktestHistory(algorithm, analyser.getSummary());
        return result;
    }

    public List<BacktestHistory> findBacktestHistoryByAlgoId(String algoId){
        List<BacktestHistory> backtestHistories = backtestHistoryRepository.findByAlgoId(algoId);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        backtestHistories.sort((o1, o2) -> LocalDateTime.parse(o2.getTime(), formatter).compareTo(LocalDateTime.parse(o1.getTime(), formatter)));
        return backtestHistories;
    }

    public long findBacktestHistoryNumByAlgoId(String algoId){
        return backtestHistoryRepository.findByAlgoId(algoId).size();
    }


    public BacktestHistory findLatestBacktestHistoryByAlgoId(String algoId) {
        return backtestHistoryRepository.findDistinctFirstByAlgoIdOrderByTimeDesc(algoId);
    }

    public ResponseEntity<byte[]> downloadResult(String json) throws UnsupportedEncodingException {
        return downloadResultHelper(JsonFormatTool.formatJson(json).getBytes(), "result.csv");
    }

    private ResponseEntity<byte[]> downloadResultHelper(byte[] body, String fileName) throws UnsupportedEncodingException {
        HttpStatus status = HttpStatus.CREATED;
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String header = request.getHeader("User-Agent").toUpperCase();
        if (header.contains("MSIE") || header.contains("TRIDENT") || header.contains("EDGE")) {
            fileName = URLEncoder.encode(fileName, "UTF-8");
            fileName = fileName.replace("+", "%20");    // IE下载文件名空格变+号问题
            status = HttpStatus.OK;
        } else {
            fileName = new String(fileName.getBytes("UTF-8"), "ISO8859-1");
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", fileName);
        headers.setContentLength(body.length);

        return new ResponseEntity(body, headers, status);
    }

    private void createSuccessfulBacktestHistory(Algorithm algorithm, Summary summary) {

        // 生成回测历史
        BacktestHistory backtestHistory = new BacktestHistory(
                algorithm.getAlgoId(),
                algorithm.getTime(),
                algorithm.getBeginDate(),
                algorithm.getEndDate(),
                algorithm.getStockStartCash(),
                algorithm.getCode(),
                summary.getTotal_returns(),
                summary.getAnnualized_returns(),
                summary.getBenchmark_total_returns(),
                summary.getAlpha(),
                summary.getSharpe(),
                summary.getMax_drawdown(),
                1);
        System.out.println(algorithm.getCode());
        backtestHistoryRepository.save(backtestHistory);

    }

    private void createFailedBacktestHistory(Algorithm algorithm) {
        BacktestHistory backtestHistory = new BacktestHistory(
                algorithm.getAlgoId(),
                algorithm.getTime(),
                algorithm.getBeginDate(),
                algorithm.getEndDate(),
                algorithm.getStockStartCash(),
                algorithm.getCode(),
                0, 0, 0, 0, 0, 0,
                0);
        backtestHistoryRepository.save(backtestHistory);
    }


}
