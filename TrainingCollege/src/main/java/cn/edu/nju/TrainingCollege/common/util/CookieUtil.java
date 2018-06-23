package cn.edu.nju.TrainingCollege.common.util;

import javax.servlet.http.Cookie;

/**
 * @author hiki on 2018-04-01
 */

public class CookieUtil {

    public static Cookie of(String name, String value) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setMaxAge(Integer.MAX_VALUE);
        cookie.setHttpOnly(true);
        return cookie;
    }

}
