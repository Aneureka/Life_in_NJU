package leftovers.datahelper.xueqiuspider.helper;

import java.io.IOException;
import java.net.*;

/**
 * Created by Hiki on 2017/6/14.
 */
public class RequestHelper {

    public static HttpURLConnection open(String urlPath) throws IOException {
        URL url = new URL(urlPath);
        return (HttpURLConnection) url.openConnection();
    }

    public static void setHeaders(HttpURLConnection conn){
        // 请求头设置
        conn.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        conn.setRequestProperty("Accept-Encoding", "gzip, deflate, sdch, br");
        conn.setRequestProperty("Cache-Control", "max-age=0");
        conn.setRequestProperty("Connection", "keep-alive");
        conn.setRequestProperty("Host", "xueqiu.com");
        conn.setRequestProperty("Upgrade-Insecure-Requests", "1");
        conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.133 Safari/537.36");
    }

    public static HttpURLConnection openWithProxy(URL url) throws IOException {
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("123.206.187.246", 3128));
        return (HttpURLConnection) url.openConnection(proxy);

    }

}
