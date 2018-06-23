package leftovers.repository;

import leftovers.util.Request;
import org.apache.http.client.CookieStore;
import org.apache.http.client.utils.URIBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by kevin on 2017/6/11.
 */
public class MarketRepository {
    private CookieStore cookieStore;

    public MarketRepository() throws IOException {
        //初始化cookie
        cookieStore = Request.getCookie("https://xueqiu.com");
    }

    public String getStockListByBoard(Map<String, String[]> params) throws IOException, URISyntaxException {
        String board = params.get("board")[0];

        return board.equals("a") ?
                getABoardStockList(params) :
                getOtherBoardStockList(params);
    }

    private String getABoardStockList(Map<String, String[]> params) throws URISyntaxException, IOException {
        //创建请求路径
        URIBuilder builder = new URIBuilder();
        builder.setScheme("https").setHost("xueqiu.com").setPath("/stock/cata/stocklist.json")
                .setParameter("page", params.get("page")[0])
                .setParameter("size", "10")
                .setParameter("order", params.get("order")[0])
                .setParameter("orderby", params.get("orderby")[0])
                .setParameter("type", "11,12")
                .setParameter("_", System.currentTimeMillis() + "");
        URI uri = builder.build();

        //发起请求
        return Request.get(uri.toString(), cookieStore);
    }

    private String getOtherBoardStockList(Map<String, String[]> params) throws URISyntaxException, IOException {
        //创建请求路径
        URIBuilder builder = new URIBuilder();
        builder.setScheme("https").setHost("xueqiu.com").setPath("/stock/quote_order.json")
                .setParameter("page", params.get("page")[0])
                .setParameter("size", "10")
                .setParameter("order", params.get("order")[0])
                .setParameter("exchange", "CN")
                .setParameter("stockType", params.get("board")[0])
                .setParameter("column", "symbol,name,current,chg,percent,last_close,open,high,low,volume,amount,market_capital,pe_ttm,high52w,low52w,hasexist")
                .setParameter("orderBy", params.get("orderby")[0])
                .setParameter("_", System.currentTimeMillis() + "");
        URI uri = builder.build();

        //发起请求
        return Request.get(uri.toString(), cookieStore);
    }
}