package leftovers.datahelper.xueqiuspider.helper;

import leftovers.datahelper.xueqiuspider.constant.*;

/**
 * Created by Hiki on 2017/5/14.
 */

public class UrlGenerator{

    public String generateMainUrl() {
        String originUrl = UrlPool.MAIN.toString();
        return originUrl;
    }

    public String generateStockQuoteUrl(String code) {
        String originUrl = UrlPool.STOCK_QUOTE_INFO.toString();
        UrlBuilder builder = new UrlBuilder(originUrl)
                .addParam("code", code);
        return builder.build();
    }

    public String generateStockKInfoUrl(String code, Period period, PriceType type, long beginTimeStamp, long endTimeStamp) {
        String originUrl = UrlPool.STOCK_K_INFO.toString();
        UrlBuilder builder = new UrlBuilder(originUrl)
                .addParam("symbol", code)
                .addParam("period", period.toString())
                .addParam("type", type.toString())
                .addParam("begin", beginTimeStamp)
                .addParam("end", endTimeStamp);
        return builder.build();

    }

    public String generateStockAllKInfoUrl(String code, Period period, PriceType type) {
        String originUrl = UrlPool.STOCK_K_INFO.toString();
        UrlBuilder builder = new UrlBuilder(originUrl)
                .addParam("symbol", code)
                .addParam("period", period.toString())
                .addParam("type", type.toString());
        return builder.build();
    }

    public String generateStockMinInfoUrl(String code) {
        String originUrl = UrlPool.STOCK_MIN_INFO.toString();
        UrlBuilder builder = new UrlBuilder(originUrl)
                .addParam("symbol", code)
                .addParam("one_min", 1);
        return builder.build();
    }

    public String generateStockPankouInfoUrl(String code) {
        String originUrl = UrlPool.STOCK_PANKOU_INFO.toString();
        UrlBuilder builder = new UrlBuilder(originUrl)
                .addParam("symbol", code);
        return builder.build();
    }

    public String generateStockIndustryInfoUrl(String code, int size) {
        String originUrl = UrlPool.STOCK_INDUSTRY_INFO.toString();
        UrlBuilder builder = new UrlBuilder(originUrl)
                .addParam("type", 1)
                .addParam("code", code)
                .addParam("size", size);
        return builder.build();
    }

    public String generateStockCompBasicInfoUrl(String code) {
        String originUrl = UrlPool.STOCK_COMP_BASIC_INFO.toString();
        UrlBuilder builder = new UrlBuilder(originUrl)
                .addParam("symbol", code);
        return builder.build();

    }

    public String generateStockNewsInfoUrl(String code, int count, int page){
        String originUrl = UrlPool.STOCK_NEWS_INFO.toString();
        UrlBuilder builder = new UrlBuilder(originUrl)
                .addParam("symbol_id", code)
                .addParam("count", count)
                .addParam("source", "%e8%87%aa%e9%80%89%e8%82%a1%e6%96%b0%e9%97%bb")
                .addParam("page", page);
        return builder.build();

    }

    public String generateStockAnnouncementInfoUrl(String code, int count, int page){
        String originUrl = UrlPool.STOCK_ANOUNCEMENT_INFO.toString();
        UrlBuilder builder = new UrlBuilder(originUrl)
                .addParam("symbol_id", code)
                .addParam("count", count)
                .addParam("source", "%e5%85%ac%e5%91%8a")
                .addParam("page", page);
        return builder.build();

    }



    public String generateHotRankInfoUrl(int size) {
        String originUrl = UrlPool.HOT_RANK_INFO.toString();
        UrlBuilder builder = new UrlBuilder(originUrl)
                .addParam("size", size)
                .addParam("type", 12);  // 10全球 12沪深 13港股
        return builder.build();

    }

    public String generateLongHuBangInfoUrl(String date) {
        String originUrl = UrlPool.LONGHUBANG_INFO.toString();
        UrlBuilder builder = new UrlBuilder(originUrl)
                .addParam("date", date);
        return builder.build();

    }

    public String generateRankInfoUrl(int size, boolean esc, Exchange exchange, StockType stockType, OrderFactor orderBy) {
        String originUrl = UrlPool.RANK_INFO.toString();
        UrlBuilder builder = new UrlBuilder(originUrl)
                .addParam("page", 1)
                .addParam("size", size)
                .addParam("order", esc ? "esc" : "desc")
                .addParam("exchange", exchange.toString())
                .addParam("stockType", stockType.toString())
                .addParam("column", "symbol%2Cname%2Ccurrent%2Cchg%2Cpercent%2Copen%2Chigh%2Clow%2Cvolume%2Camount%2Cmarket_capital%2Cpe_ttm")
                .addParam("orderBy", orderBy.toString());
        return builder.build();

    }

    public String generateNewsInfoUrl() {
        String originUrl = UrlPool.NEWS_INFO.toString();
        return originUrl;
    }
}
