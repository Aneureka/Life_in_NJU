package leftovers.datahelper.xueqiuspider.helper;

import leftovers.datahelper.xueqiuspider.constant.UrlPool;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by Hiki on 2017/5/13.
 * 主要用于连接雪球，获取相应的Cookie
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
        HttpURLConnection conn = RequestHelper.open(UrlPool.MAIN.toString());
        RequestHelper.setHeaders(conn);
        conn.connect();
        List<String> cookies = conn.getHeaderFields().get("Set-Cookie");
        this.cookie = StringUtils.join(cookies.toArray(), "; ");
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
