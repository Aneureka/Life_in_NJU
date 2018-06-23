package leftovers.util;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by kevin on 2017/6/10.
 */
public class JSONResult {
    public static String fillResultString(Integer status, String message, Object result) {
        JSONObject jsonObject = new JSONObject() {{
            put("status", status);
            put("message", message);
            put("result", result);
        }};
        return jsonObject.toString();
    }
}
