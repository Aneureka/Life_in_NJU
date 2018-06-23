package leftovers.datahelper.showapigetter;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Hiki on 2017/6/7.
 */
@Component
public class ShowapiJsonProducer {

//    private final static String APPID = "38328";
//    private final static String SHOWAPI_SIGN = "dafb26027d464c008f4870b15e41a89d";
    private final static String APPID = "40181";
    private final static String SHOWAPI_SIGN = "bdddd4eb61cd4757bf21c9fe3199ef35";

    private final static String HOST = "http://route.showapi.com";


    private String buildNewsUrl(int page) {

        StringBuilder newsUrl = new StringBuilder(HOST);

        newsUrl.append("/109-35?")
                .append("showapi_appid=").append(APPID).append("&")
                .append("channelId=&channelName=%e8%b4%a2%e7%bb%8f&title=&")
                .append("page=").append(page).append("&")
                .append("needContent=&needHtml=1&needAllList=&maxResult=20&")
                .append("showapi_sign=").append(SHOWAPI_SIGN);
        System.out.println(newsUrl.toString());
        return newsUrl.toString();
    }


    public String getMarketNewsInfoJson(int page) throws IOException {

        // 生成链接
        URL url = new URL(buildNewsUrl(page));

        // 连接
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.connect();

        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
        StringBuilder sb = new StringBuilder();
        String str = null;
        while ((str = br.readLine()) != null) {
            sb.append(str);
        }
        br.close();
        if (conn != null) conn.disconnect();
        return sb.toString();
    }


    public static void main(String[] args) {
        ShowapiJsonProducer sjp = new ShowapiJsonProducer();
        try {
            String content = sjp.getMarketNewsInfoJson(1);
            sjp.getMarketNewsInfoJson(1);
            sjp.getMarketNewsInfoJson(1);
            System.out.println(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
