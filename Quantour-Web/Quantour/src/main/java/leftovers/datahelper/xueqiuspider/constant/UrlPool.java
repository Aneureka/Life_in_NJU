package leftovers.datahelper.xueqiuspider.constant;

/**
 * Created by Hiki on 2017/5/13.
 */
public enum UrlPool {

    // 主页
    MAIN("https://xueqiu.com"),

    /*-----------------------------------------------个股信息---------------------------------------------------*/

    // 股票实时总体信息
    // https://xueqiu.com/v4/stock/quote.json?code=SZ000001
    STOCK_QUOTE_INFO("https://xueqiu.com/v4/stock/quote.json"),

    // K线图数据
    // https://xueqiu.com/stock/forchartk/stocklist.json?symbol=SZ000001&period=1day&type=normal&begin=1463108717644&end=1494644717644
    // https://xueqiu.com/stock/forchartk/stocklist.json?symbol=SZ000001&period=1day&type=normal
    STOCK_K_INFO("https://xueqiu.com/stock/forchartk/stocklist.json"),

    // 分时图数据
    // https://xueqiu.com/stock/forchart/stocklist.json?symbol=SZ000001&one_min=1
    STOCK_MIN_INFO("https://xueqiu.com/stock/forchart/stocklist.json"),

    // 盘口数据
    // https://xueqiu.com/stock/pankou.json?symbol=SZ000001
    STOCK_PANKOU_INFO("https://xueqiu.com/stock/pankou.json"),

    // 行业数据
    // https://xueqiu.com/stock/industry/stockList.json?type=1&code=SZ000001&size=8
    STOCK_INDUSTRY_INFO("https://xueqiu.com/stock/industry/stockList.json"),

    // 公司简介
    // https://xueqiu.com/S/SZ000001/GSJJ
    // https://xueqiu.com/stock/f10/compinfo.json?symbol=SZ000001
    STOCK_COMP_BASIC_INFO("https://xueqiu.com/stock/f10/compinfo.json"),

    // 股票新闻
    // https://xueqiu.com/statuses/stock_timeline.json?symbol_id=SZ000001&count=200&page=1
    STOCK_NEWS_INFO("https://xueqiu.com/statuses/stock_timeline.json"),

    // 股票公告
    // https://xueqiu.com/statuses/stock_timeline.json?symbol_id=SZ000001&count=10&source=公告&page=1
    STOCK_ANOUNCEMENT_INFO("https://xueqiu.com/statuses/stock_timeline.json"),

    /*--------------------------------------------------行情信息-----------------------------------------------------*/

    // 热股榜
    // https://xueqiu.com/stock/rank.json?size=10&type=12
    HOT_RANK_INFO("https://xueqiu.com/stock/rank.json"),

    // 龙虎榜
    // https://xueqiu.com/stock/f10/bizunittrdinfo.json?date=20160509
    LONGHUBANG_INFO("https://xueqiu.com/stock/f10/bizunittrdinfo.json"),

    // 各类排行
    // https://xueqiu.com/hq#exchange=CN&plate=1_3_0&firstName=1&secondName=1_3&type=sha&order=desc&orderby=percent
    // https://xueqiu.com/stock/quote_order.json?page=1&size=10&order=desc&exchange=CN&stockType=cyb&column=symbol%2Cname%2Ccurrent%2Cchg%2Cpercent%2Copen%2Chigh%2Clow%2Cvolume%2Camount%2Cmarket_capital%2Cpe_ttm&orderBy=percent
    RANK_INFO("https://xueqiu.com/stock/quote_order.json"),

    // 股票新闻信息
    // https://xueqiu.com/statuses/topic.json
    NEWS_INFO("https://xueqiu.com/statuses/topic.json"),

    // 行业列表信息
    // https://xueqiu.com/stock/screener/industries.json?category=SH
    INDUSTRY_INFO("https://xueqiu.com/stock/screener/industries.json");

    private String url;

    private UrlPool(String url){
        this.url = url;
    }

    @Override
    public String toString(){
        return url;
    }







}
