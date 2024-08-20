package com.match_management.demo.cookie;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

public class CookieShop {
    public static void bake(final HttpServletResponse response,
                            final String refreshToken) {
        final Cookie cookie = new Cookie("refreshToken", refreshToken);

        cookie.setMaxAge(14 * 24 * 60 * 60);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setSecure(true);

        response.addCookie(cookie);
    }
}
