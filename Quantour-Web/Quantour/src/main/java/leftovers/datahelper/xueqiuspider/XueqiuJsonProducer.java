package leftovers.datahelper.xueqiuspider;

/**
 * Created by Hiki on 2017/5/14.
 */

public interface XueqiuJsonProducer {

    /**
     * 获取股票总体信息Json
     * @param code 股票代码
     * @return
     */
    public String getStockQuoteJson(String code);

    /**
     * 获取股票k线数据Json
     * @param code 股票代码
     * @param period 周期：1day  1week  1month
     * @param type 复权类型：before前复权 normal不复权 after后复权
     * @param beginTimeStamp 开始时间戳
     * @param endTimeStamp 结束时间戳
     * @return
     */
    public String getStockKInfoJson(String code, String period, String type, long beginTimeStamp, long endTimeStamp);

    /**
     * 获得股票所有k线数据Json
     * @param code 股票代码
     * @param period 周期：1day 1week 1month
     * @param type 复权类型：before前复权 normal不复权 after后复权
     * @return
     */
    public String getStockAllKInfoJson(String code, String period, String type);


    /**
     * 获取股票分时数据Json
     * @param code 股票代码
     * @return
     */
    public String getStockMinInfoJson(String code);

    /**
     * 获取股票盘口数据Json
     * @param code 股票代码
     * @return
     */
    public String getStockPankouInfoJson(String code);

    /**
     * 获取股票行业信息Json
     * @param code 股票代码
     * @param size 同行业股票的显示数量
     * @return
     */
    public String getStockIndustryInfoJson(String code, int size);

    /**
     * 获取股票公司信息Json
     * @param code 股票代码
     * @return
     */
    public String getStockCompBasicInfoJson(String code);


    /**
     * 获取股票新闻Json
     * @param code 股票代码
     * @param count 显示的新闻条目个数
     * @return
     */
    public String getStockNewsInfoJson(String code, int count, int page);


    /**
     * 获取股票公告Json
     * @param code 股票代码
     * @param count 显示的公告条目个数
     * @return
     */
    public String getStockAnnouncementInfoJson(String code, int count, int page);


    /**
     * 获取当天沪深热股榜信息Json
     * @param size 热股榜股票显示数量
     * @return
     */
    public String getHotRankInfoJson(int size);

    /**
     * 获取龙虎榜信息Json
     * @param date 日期 格式：20160509
     * @return
     */
    public String getLongHuBangInfoJson(String date);

    /**
     * 获取排行信息Json
     * @param size 排行显示的股票数
     * @param esc 是否正序
     * @param exchange 股票交易平台：沪深CN 香港HK 美国US
     * @param stockType 股票类型：创业板cyb 沪Asha 沪Bshb 深Asza 深Bszb 中小板zxb
     * @param orderBy 排序方式：涨跌幅percent 成交额amount 成交量volumn
     * @return
     */
    public String getRankInfoJson(int size, boolean esc, String exchange, String stockType, String orderBy);

    /**
     * 获取新闻信息Json
     * @return
     */
    public String getNewsInfoJson();
}
