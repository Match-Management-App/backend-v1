package com.match_management.demo.openApi.exception.KakaoApi;

import com.match_management.demo.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum KakaoApiErrorCode implements ErrorCode {
    INVALID_TOKEN(1300, "잘못된 access token 입니다"),
    INVALID_DATA_TYPE(1301, "잘못된 data type 입니다");

    private final int errorCode;
    private final String errorMsg;
}
