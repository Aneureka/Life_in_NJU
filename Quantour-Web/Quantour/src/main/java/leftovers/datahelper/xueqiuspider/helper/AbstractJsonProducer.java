package leftovers.datahelper.xueqiuspider.helper;

import com.sun.org.apache.regexp.internal.RE;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.zip.GZIPInputStream;

/**
 * Created by Hiki on 2017/5/14.
 */

public class AbstractJsonProducer {

    CookieProvider cookieProvider;

    public AbstractJsonProducer() {
        this.cookieProvider = new CookieProvider();
    }

    public String getJson(String urlPath) throws IOException {
        HttpURLConnection conn = RequestHelper.open(urlPath);
        setHeadersAndCookie(conn);

        // 连接
        conn.connect();
        // 若链接失败，重新获取Cookie并打印相关信息
        if (conn.getResponseCode() >= 400){
            // 更新Cookie
            updateCookie(conn);
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            System.out.println(conn.getResponseCode());
            String line = null;
            while((line = br.readLine()) != null){
                System.out.println(line);
            }
        }


        // 判断是不是GZIP，如果是解压GZIP数据流并包装
        InputStream is = conn.getInputStream();
        if (isGzipped(conn))
            is = new GZIPInputStream(is);
        BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
        StringBuilder sb = new StringBuilder();
        String str = null;
        while ((str = br.readLine()) != null) {
            sb.append(str);
        }
        br.close();
        if (conn != null) conn.disconnect();
        return sb.toString();

    }

    /**
     * 初始化并设置Cookie
     * @param conn
     */
    private void setHeadersAndCookie(HttpURLConnection conn){
        RequestHelper.setHeaders(conn);
        conn.setRequestProperty("Cookie", cookieProvider.getCookie());
    }

    private void updateCookie(HttpURLConnection conn){
        cookieProvider.updateCookie();
        conn.setRequestProperty("Cookie", cookieProvider.getCookie());
    }

    /**
     * 判断网站内容是否被压缩
     * @param conn
     * @return
     */
    private boolean isGzipped(HttpURLConnection conn){
        String contentEncoding = conn.getHeaderField("Content-Encoding");
        if (contentEncoding == null) return false;
        return contentEncoding.toLowerCase().contains("gzip") ? true : false;
    }

}
