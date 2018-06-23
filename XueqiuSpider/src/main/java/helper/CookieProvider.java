package helper;

import constant.UrlPool;
import com.sun.deploy.util.StringUtils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by Hiki on 2017/5/13.
 * 主要用于连接雪球，获取相应的Cookie
 * TODO 模拟登陆
 * TODO 当前的策略是每次访问都要连接雪球网获取Cookie
 */

public class CookieProvider {


    private String cookie;

    public CookieProvider() {
        try {
            init();
        } catch (IOException e) {
            System.out.println("访问雪球网失败...");
        }
    }

    /**
     * 连接到雪球网并初始化Cookie
     */
    private void init() throws IOException {
        URL url = new URL(UrlPool.MAIN.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        ConnectInitializer.setHeaders(conn);
        conn.connect();
        List<String> cookies = conn.getHeaderFields().get("Set-Cookie");
        this.cookie = StringUtils.join(cookies, "; ");
    }

    /**
     * 更新Cookie
     */
    public void updateCookie(){
        try {
            init();
        } catch (IOException e) {
            System.out.println("访问雪球网失败...");
        }
    }

    public String getCookie() {
        return cookie;
    }


}
