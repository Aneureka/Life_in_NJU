package leftovers.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by kevin on 2017/6/14.
 */
public class FormatChecker {
    /**
     * 正则表达式：验证用户名
     */
    public static final String REGEX_USERNAME = "^[a-zA-Z]\\w{5,17}$";

    /**
     * 正则表达式：验证密码
     */
    public static final String REGEX_PASSWORD = "^[a-zA-Z0-9]{6,16}$";

    /**
     * 正则表达式：验证手机号
     */
    public static final String REGEX_MOBILE = "^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";

    /**
     * 正则表达式：验证邮箱
     */
    public static final String REGEX_EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

    public static boolean check(String type, String toCheck) {
        boolean tag = true;
        final Pattern pattern = Pattern.compile(type);
        final Matcher mat = pattern.matcher(toCheck);
        if (!mat.find()) {
            tag = false;
        }
        return tag;
    }
}
