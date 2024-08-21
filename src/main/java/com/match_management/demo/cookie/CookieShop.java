package com.match_management.demo.cookie;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;

public class CookieShop {
    public static void bake(final HttpServletResponse response,
                            final int expireTime,
                            final String type,
                            final String token) {
        final ResponseCookie cookie = ResponseCookie.from(type, token)
                .maxAge(expireTime)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .build();

        response.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }
}
