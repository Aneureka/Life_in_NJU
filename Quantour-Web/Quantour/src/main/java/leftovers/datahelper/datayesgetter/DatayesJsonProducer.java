package leftovers.datahelper.datayesgetter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import leftovers.model.StockNewsIndexInfo;
import leftovers.service.StockService;
import leftovers.util.StockSymbolParser;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Hiki on 2017/6/2.
 */

@Component
public class DatayesJsonProducer {

    private DatayesConnector dc;

    public DatayesJsonProducer() {
        this.dc = DatayesConnector.getInstance();
    }

    public String getDyStockNewsJson(String ticker, String beginDate, String endDate){
        String url = UrlBuilder.buildNewsUrl(ticker, beginDate, endDate);
        System.out.println(url);
        return filterNewsInfo(dc.getContent(url));
    }


    private String filterNewsInfo(String newsJson){

        String retCode = (JSONObject.parseObject(newsJson)).getString("retCode");
        String retMsg = (JSONObject.parseObject(newsJson)).getString("retMsg");

        // 提取出一个list
        List<StockNewsIndexInfo> news = (JSONObject.parseObject(newsJson)).getJSONArray("data").toJavaList(StockNewsIndexInfo.class);

        List<StockNewsIndexInfo> filteredNews = new ArrayList<>();

        // 去除名字重复的项
        for (StockNewsIndexInfo item : news) {
            if (!filteredNews.stream().map(e -> e.getNewsTitle()).collect(Collectors.toList()).contains(item.getNewsTitle())){
                filteredNews.add(item);
            }
        }

        return new org.json.JSONObject()
                .put("retCode", retCode)
                .put("retMsg", retMsg)
                .put("data", filteredNews)
                .toString();

    }

}
