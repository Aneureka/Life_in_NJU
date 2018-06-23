package leftovers.util;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by kevin on 2017/6/11.
 */
public class RequestTest {

    @Test
    public void get1() throws Exception {
        String response = Request.get("https://xueqiu.com/stock/quote_order.json?page=1&size=30&order=desc&exchange=CN&stockType=cyb&column=symbol%2Cname%2Ccurrent%2Cchg%2Cpercent%2Clast_close%2Copen%2Chigh%2Clow%2Cvolume%2Camount%2Cmarket_capital%2Cpe_ttm%2Chigh52w%2Clow52w%2Chasexist&orderBy=percent&_=1497155610517");
    }

    @Test
    public void get() throws Exception {
        String response = Request.get("https://xueqiu.com/stock/quote_order.json?page=1&size=30&order=desc&exchange=CN&stockType=cyb&column=symbol%2Cname%2Ccurrent%2Cchg%2Cpercent%2Clast_close%2Copen%2Chigh%2Clow%2Cvolume%2Camount%2Cmarket_capital%2Cpe_ttm%2Chigh52w%2Clow52w%2Chasexist&orderBy=percent&_=1497155610517"
        , Request.getCookie("https://xueqiu.com"));
    }

    @Test
    public void getCookie() throws Exception {
        Request.getCookie("https://xueqiu.com");
    }

}