package cn.edu.nju.util;

import javax.servlet.http.Cookie;

/**
 * @author hiki on 2017-12-30
 */

public class CookieHelper {

    // return [null] if not exists
    public static String getValueInCookies(Cookie[] cookies, String name) {
        Cookie cookie = findOne(cookies, name);
        return cookie != null ? cookie.getValue() : null;
    }


    public static Cookie findOne(Cookie[] cookies, String name) {
        if (name == null)
            return null;

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (name.equals(cookie.getName())) {
                    return cookie;
                }
            }
        }
        return null;
    }
}
