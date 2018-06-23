package leftovers.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import leftovers.datahelper.datayesgetter.DatayesJsonProducer;
import leftovers.datahelper.xueqiuspider.XueqiuJsonProducer;
import leftovers.model.StockDData;
import leftovers.service.stockpricepredictimpl.StockPricePredictor;
import leftovers.repository.StockRepository;
import leftovers.util.StockSymbolParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by kevin on 2017/5/12.
 */
@Service
public class StockService {

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private XueqiuJsonProducer xueqiuJsonProducer;

    @Autowired
    private DatayesJsonProducer datayesJsonProducer;

    @Autowired
    private StockPricePredictor stockPricePredictor;

    public List<StockDData> getStock1DData(String code, LocalDate startDate, LocalDate endDate) {
        return stockRepository.findByPkCode(code);
    }

    public String getStockQuoteInfo(String code) {
        return xueqiuJsonProducer.getStockQuoteJson(code);
    }

    public String getStockKInfo(String code, String period, String type, long beginTimeStamp, long endTimeStamp) {
        return xueqiuJsonProducer.getStockKInfoJson(code, period, type, beginTimeStamp, endTimeStamp);
    }

    public String getStockAllKInfo(String code, String period, String type){
        return xueqiuJsonProducer.getStockAllKInfoJson(code, period, type);
    }

    public String getStockMinInfo(String code) {
        return xueqiuJsonProducer.getStockMinInfoJson(code);
    }

    public String getStockPankouInfo(String code) {
        return xueqiuJsonProducer.getStockPankouInfoJson(code);
    }

    public String getStockIndustryInfo(String code, int size) {
        return xueqiuJsonProducer.getStockIndustryInfoJson(code, size);
    }

    public String getStockCompBasicInfo(String code) {
        return xueqiuJsonProducer.getStockCompBasicInfoJson(code);
    }

    public String getStockNewsInfo(String code, int count, int page){
        return xueqiuJsonProducer.getStockNewsInfoJson(code, count, page);
    }

    public String getStockAnnouncementInfo(String code, int count, int page){
        return xueqiuJsonProducer.getStockAnnouncementInfoJson(code, count, page);
    }

    public String getStockNewsSentimentScoreInfo(String code, String beginDate, String endDate){
        // 先对code进行处理，去除前面的英文编号
        String codeNum = StockSymbolParser.getStockCodeNum(code);
        // 获取相应的Json
        String rawJson = datayesJsonProducer.getDyStockNewsJson(codeNum, beginDate, endDate);
        // 对Json进行相应处理
        JSONObject jobj = JSON.parseObject(rawJson);
        // 获取retCode
        int retCode = jobj.getIntValue("retCode");
        if (retCode < 0) {
            System.out.println("获取新闻指数失败！");
            return (new org.json.JSONObject().put("retCode", "0")).toString();
        }

        // 创建List保存相关度和情感指数
        List<NewsIndex> newsIndexes = new ArrayList<>();
        JSONArray jarr = jobj.getJSONArray("data");
        for (int i = 0; i < jarr.size(); i++) {
            JSONObject each = jarr.getJSONObject(i);
            double relatedScore = each.getDoubleValue("relatedScore");
            double sentimentScore = each.getDoubleValue("sentimentScore");
            newsIndexes.add(new NewsIndex(relatedScore, sentimentScore));
        }

        // 计算出总体情感指数
        double finalSentimentScore = newsIndexes.stream().mapToDouble(e -> e.sentimentScore * e.relatedScore).sum() /
                                      newsIndexes.stream().mapToDouble(e -> e.relatedScore).sum();

        // 生成一个JsonString
        String json = new org.json.JSONObject()
                .put("retCode", "1")
                .put("sentimentScore", finalSentimentScore)
                .toString();

        return json;
    }

    public String getStockNewsAndScoreInfo(String code, String beginDate, String endDate){
        // 先对code进行处理，去除前面的英文编号
        String codeNum = StockSymbolParser.getStockCodeNum(code);
        // 获取相应的Json
        return datayesJsonProducer.getDyStockNewsJson(codeNum, beginDate, endDate);
    }


    public String getNP1Price(String code, int num){

        // 获取最后num天的日期、收盘价数据
        LocalDate date = LocalDate.now().minusDays(num);
        List<StockDData> data = stockRepository.findByPkCodeAndPkDateAfter(code, date).stream().filter(e -> e.getClose() != null).collect(Collectors.toList());
        List<LocalDate> dates = data.stream().map(e -> e.getPk().getDate()).collect(Collectors.toList());
        List<Double> prices = data.stream().mapToDouble(e -> e.getClose()).boxed().collect(Collectors.toList());

        // 预测结果
        stockPricePredictor.initialize(code);
        // 如果不适合预测，返回
        if (!stockPricePredictor.fitToPredict() || data.size() < num / 3) {
            return new org.json.JSONObject().put("retCode", 0).toString();
        }

        int n = prices.size();
        List<Double> predictedNP1Price = stockPricePredictor.predictNP1Price(n);
        dates.add(dates.get(dates.size()-1).plusDays(1));
        prices.add(0.0);

        String json = new org.json.JSONObject()
                .put("retCode", 1)
                .put("dates", dates)
                .put("prices", prices)
                .put("predicted_prices", predictedNP1Price)
                .toString();

        return json;

    }

    class NewsIndex {

        double relatedScore;
        double sentimentScore;

        NewsIndex(double relatedScore, double sentimentScore){
            this.relatedScore = relatedScore;
            this.sentimentScore = sentimentScore;
        }

    }

}



//    public String getNP1Price(String code, int num){
//        // 获取最后一个月的日期、收盘价数据
//        int m = 10;
//        LocalDate date = LocalDate.now().minusDays(num);
//        List<StockDData> data = stockRepository.findByPkCodeAndPkDateAfter(code, date);
//        List<LocalDate> dates = data.stream().map(e -> e.getPk().getDate()).collect(Collectors.toList());
//        List<Double> prices = data.stream().mapToDouble(e -> e.getClose()).boxed().collect(Collectors.toList());
//
//        // 预测结果
//        stockPricePredictor.initialize(code);
//        // 如果不适合预测，返回
//        if (!stockPricePredictor.fitToPredict()) {
//            return new org.json.JSONObject().put("retCode", 0).toString();
//        }
//
//        List<Double> nextMPrice = stockPricePredictor.predictNextMPrice(m);
//
//        LocalDate nextDate = dates.get(dates.size()-1).plusDays(m / 2);
//        double nextPrice = nextMPrice.stream().mapToDouble(e -> e).average().orElse(0);
//        dates.add(nextDate);
//        prices.add((double) Math.round( nextPrice * 100 ) / 100);
//
//        String json = new org.json.JSONObject()
//                .put("retCode", 1)
//                .put("dates", dates)
//                .put("prices", prices)
//                .toString();
//
//        return json;
//
//    }