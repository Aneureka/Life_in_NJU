package leftovers.datahelper.datayesgetter;

/**
 * Created by Hiki on 2017/6/2.
 */
public class UrlBuilder {

    // 新闻情感url
    // ?field=&secID=&exchangeCD=&ticker=600000&secShortName=&beginDate=20150301&endDate=20150305
    public static String buildNewsUrl(String ticker, String beginDate, String endDate) {
        String host = "https://api.wmcloud.com/data/v1/api/subject/getNewsByTickers.json";
        StringBuilder sb = new StringBuilder(host);
        sb.append("?field=relatedScore,sentimentScore,newsTitle,newsPublishSite,newsPublishTime&secID=&exchangeCD=")
                .append("&ticker=").append(ticker).append("&secShortName=")
                .append("&beginDate=").append(beginDate)
                .append("&endDate=").append(endDate);
        return sb.toString();
    }
}
