package com.match_management.demo.openApi.exception.json;

import com.match_management.demo.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum JsonErrorCode implements ErrorCode {
    DESERIALIZE_FAIL(1200, "json 맵핑에 실패 했습니다.");

    private final int errorCode;
    private final String errorMsg;
}
