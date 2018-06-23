package leftovers.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.http.ResponseEntity;

import java.io.*;
import java.net.CookieStore;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by kevin on 2017/6/8.
 */
public class Request {

    public static String get(String url) throws IOException {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) ...");
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
        con.setRequestMethod("GET");

        return readResponseBody(con.getInputStream());
    }

    public static String get(String url, org.apache.http.client.CookieStore cookieStore) throws IOException {
        CloseableHttpClient httpClient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
        HttpGet httpGet = new HttpGet(url);
        HttpResponse response = httpClient.execute(httpGet);

        HttpEntity responseEntity = response.getEntity();

        return responseEntity == null ?
                "" :
                EntityUtils.toString(responseEntity);
    }

    public static org.apache.http.client.CookieStore getCookie(String url) throws IOException {
        org.apache.http.client.CookieStore cookieStore = new BasicCookieStore();

        CloseableHttpClient httpClient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
        HttpGet httpGet = new HttpGet(url);
        httpClient.execute(httpGet);

        return cookieStore;
    }

    // 读取输入流中的数据
    private static String readResponseBody(InputStream inputStream) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        String inputLine;
        StringBuffer response = new StringBuffer();


        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return response.toString();
    }

}
