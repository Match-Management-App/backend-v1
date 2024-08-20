package com.match_management.demo.auth.jwt.exception;

import com.match_management.demo.exception.CustomException;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties.Jwt;

public class JwtException extends CustomException {
    public JwtException(final JwtErrorCode jwtErrorCode) {
        super(jwtErrorCode);
    }

    public static class InvalidedToken extends JwtException{
        public InvalidedToken() {
            super(JwtErrorCode.ILLEGAL_TOKEN);
        }
    }

    public static class ExpiredToken extends JwtException {
        public ExpiredToken() {
            super(JwtErrorCode.EXPIRED_TOKEN);
        }
    }

    public static class WrongSignedToken extends JwtException {
        public WrongSignedToken() {
            super(JwtErrorCode.WRONG_SIGNED_TOKEN);
        }
    }

    public static class UnSupportedToken extends JwtException {
        public UnSupportedToken() {
            super(JwtErrorCode.UNSUPPORTED_TOKEN);
        }
    }

    public static class IllegalToken extends JwtException {
        public IllegalToken() {
            super(JwtErrorCode.ILLEGAL_TOKEN);
        }
    }
}
