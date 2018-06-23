package leftovers.datahelper.xueqiuspider;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import leftovers.datahelper.xueqiuspider.constant.*;
import leftovers.datahelper.xueqiuspider.helper.AbstractJsonProducer;
import leftovers.datahelper.xueqiuspider.helper.UrlGenerator;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Created by Hiki on 2017/5/14.
 */

@Component
public class XueqiuJsonProducerImpl implements XueqiuJsonProducer {

    AbstractJsonProducer ajp;

    UrlGenerator generator;

    public XueqiuJsonProducerImpl() {
        ajp = new AbstractJsonProducer();
        generator = new UrlGenerator();
    }

    @Override
    public String getStockQuoteJson(String code) {
        String url = generator.generateStockQuoteUrl(code);
        String originJson = getJson(url);
        // 这个情况比较特殊，需要进一步处理Json
        JSONObject jsonObject = JSON.parseObject(originJson).getJSONObject(code);
        return jsonObject.toJSONString();
    }

    @Override
    public String getStockKInfoJson(String code, String period, String type, long beginTimeStamp, long endTimeStamp) {
        String url = generator.generateStockKInfoUrl(code, Period.getEnum(period), PriceType.getEnum(type), beginTimeStamp, endTimeStamp);
        return getJson(url);
    }

    @Override
    public String getStockAllKInfoJson(String code, String period, String type) {
        String url = generator.generateStockAllKInfoUrl(code, Period.getEnum(period), PriceType.getEnum(type));
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
    public String getStockNewsInfoJson(String code, int count, int page) {
        String url = generator.generateStockNewsInfoUrl(code, count, page);
        return getJson(url);
    }

    @Override
    public String getStockAnnouncementInfoJson(String code, int count, int page) {
        String url = generator.generateStockAnnouncementInfoUrl(code, count, page);
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
    public String getRankInfoJson(int size, boolean esc, String exchange, String stockType, String orderBy) {
        String url = generator.generateRankInfoUrl(size, esc, Exchange.getEnum(exchange), StockType.getEnum(stockType), OrderFactor.getEnum(orderBy));
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
