package leftovers.service;

import com.alibaba.fastjson.JSONObject;
import leftovers.model.WatchlistItem;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by kevin on 2017/6/9.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class UserServiceTest {

    @Autowired
    UserService tested;

    @Test
    public void getWatchlist1() throws Exception {
        List<WatchlistItem> res = tested.getWatchlist("kevin1");

        String method = "getWatchlist(\"kevin1\")";
        String caseDescription = "normal";
        Object result = res;

        log(method, caseDescription, res);
    }

    @Test
    public void getWatchlist2() throws Exception {
        List<WatchlistItem> res = tested.getWatchlist("kevin100");

        String method = "getWatchlist(\"kevin100\")";
        String caseDescription = "user not found";
        Object result = res;

        log(method, caseDescription, res);
    }

    @Test
    public void addWatchlistItem1() throws Exception {
        WatchlistItem res = tested.addWatchlistItem("kevin1", "SZ000044");

        String method = "addWatchlistItem(\"kevin1\", \"SZ000044\")";
        String caseDescription = "normal";
        Object result = res;

        log(method, caseDescription, res);
    }

    @Test
    public void addWatchlistItem2() throws Exception {
        WatchlistItem res = tested.addWatchlistItem("kevin100", "SZ000044");

        String method = "addWatchlistItem(\"kevin100\", \"SZ000044\")";
        String caseDescription = "user not found";
        Object result = res;

        log(method, caseDescription, res);
    }

    @Test
    public void addWatchlistItem3() throws Exception {
        WatchlistItem res = tested.addWatchlistItem("kevin1", "SZ000044");

        String method = "addWatchlistItem(\"kevin1\", \"SZ000044\")";
        String caseDescription = "item has been added";
        Object result = res;

        log(method, caseDescription, res);
    }

    @Test
    public void removeWatchlistItem1() throws Exception {
        List<WatchlistItem> res = tested.removeWatchlistItem("kevin1", "SZ000001");
        tested.addWatchlistItem("kevin1", "SZ000001");

        String method = "removeWatchlistItem(\"kevin1\", \"SZ000001\")";
        String caseDescription = "normal";
        Object result = res;

        log(method, caseDescription, res);
    }

    @Test
    public void removeWatchlistItem2() throws Exception {
        List<WatchlistItem> res = tested.removeWatchlistItem("kevin100", "SZ000001");

        String method = "removeWatchlistItem(\"kevin100\", \"SZ000001\")";
        String caseDescription = "user not found";
        Object result = res;

        log(method, caseDescription, res);
    }

    @Test
    public void removeWatchlistItem3() throws Exception {
        List<WatchlistItem> res = tested.removeWatchlistItem("kevin1", "SZ0000010");

        String method = "removeWatchlistItem(\"kevin1\", \"SZ0000010\")";
        String caseDescription = "code no found";
        Object result = res;

        log(method, caseDescription, res);
    }

    private void log(String method, String caseDescription, Object o){
        String prefix = "[Test UserService Method]";

        StringBuffer log = new StringBuffer();
        log.append("\n");
        log.append(prefix).append("method: ").append(method).append("\n");
        log.append(prefix).append("case: ").append(caseDescription).append("\n");
        log.append(prefix).append("result: ").append(JSONObject.toJSONString(o)).append("\n");
        log.append("\n");

        System.out.print(log.toString());
    }

}