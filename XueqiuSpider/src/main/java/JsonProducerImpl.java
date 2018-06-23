import constant.*;
import helper.AbstractJsonProducer;
import helper.UrlGenerator;

import java.io.IOException;

/**
 * Created by Hiki on 2017/5/14.
 */
public class JsonProducerImpl implements JsonProducer {

    AbstractJsonProducer ajp;

    UrlGenerator generator;

    public JsonProducerImpl() {
        ajp = new AbstractJsonProducer();
        generator = new UrlGenerator();
    }

    @Override
    public String getStockQuoteJson(String code) {
        String url = generator.generateStockQuoteUrl(code);
        return getJson(url);
    }

    @Override
    public String getStockKInfoJson(String code, Period period, PriceType type, long beginTimeStamp, long endTimeStamp) {
        String url = generator.generateStockKInfoUrl(code, period, type, beginTimeStamp, endTimeStamp);
        return getJson(url);
    }

    @Override
    public String getStockAllKInfoJson(String code, Period period, PriceType type) {
        String url = generator.generateStockAllKInfoUrl(code, period, type);
        return getJson(url);
    }

    @Override
    public String getStockMinInfoJson(String code) {
        String url = generator.generateStockMinInfoUrl(code);
        return getJson(url);
    }

    @Override
    public String getStockPankouInfoJson(String code) {
        String url = generator.generateStockPankouInfoUrl(code);
        return getJson(url);
    }

    @Override
    public String getStockIndustryInfoJson(String code, int size) {
        String url = generator.generateStockIndustryInfoUrl(code, size);
        return getJson(url);
    }

    @Override
    public String getStockCompBasicInfoJson(String code) {
        String url = generator.generateStockCompBasicInfoUrl(code);
        return getJson(url);
    }

    @Override
    public String getStockNewsInfoJson(String code, int count) {
        String url = generator.generateStockNewsInfoUrl(code, count);
        return getJson(url);
    }

    @Override
    public String getStockAnnouncementInfoJson(String code, int count) {
        String url = generator.generateStockAnnouncementInfoUrl(code, count);
        return getJson(url);
    }

    @Override
    public String getHotRankInfoJson(int size) {
        String url = generator.generateHotRankInfoUrl(size);
        return getJson(url);
    }

    @Override
    public String getLongHuBangInfoJson(String date) {
        String url = generator.generateLongHuBangInfoUrl(date);
        return getJson(url);
    }

    @Override
    public String getRankInfoJson(int size, boolean esc, Exchange exchange, StockType stockType, OrderFactor orderBy) {
        String url = generator.generateRankInfoUrl(size, esc, exchange, stockType, orderBy);
        return getJson(url);
    }

    @Override
    public String getNewsInfoJson() {
        String url = generator.generateNewsInfoUrl();
        return getJson(url);
    }


    private String getJson(String url){

        String json = "";
        try {
            json = ajp.getJson(url);
        } catch (IOException e) {
            System.out.println("获取Json失败！当前Url: " + url);
        }

        return json;
    }

}
