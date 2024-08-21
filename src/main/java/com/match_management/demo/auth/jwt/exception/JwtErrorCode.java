package com.match_management.demo.auth.jwt.exception;

import com.match_management.demo.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum JwtErrorCode implements ErrorCode {
    INVALIDED_TOKEN(1400, "유효하지 않은 jwt 토큰 입니다."),
    EXPIRED_TOKEN(1401, "만료된 jwt 토큰 입니다."),
    WRONG_SIGNED_TOKEN(1402, "서명이 잘못된 jwt 토큰입니다."),
    UNSUPPORTED_TOKEN(1403, "지원 하지 않는 jwt 토큰입니다."),
    ILLEGAL_TOKEN(1404, "잘못된 jwt 토큰입니다."),
    UNMATCHED_TOKEN(1405, "발행한 토큰과 다른 accessToken 입니다"),
    INVALIDED_USER(1406, "발행 정보와 refreshToken 의 정보가 다릅니다");

    private final int errorCode;
    private final String errorMsg;
}
