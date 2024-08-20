package com.match_management.demo.record.exception;

import com.match_management.demo.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RecordErrorCode implements ErrorCode {
    NO_RECORD(1700, "존재하지 않는 record 입니다");

    private final int errorCode;
    private final String errorMsg;
}
