package com.match_management.demo.match.exception;

import com.match_management.demo.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MatchErrorCode implements ErrorCode {
    NO_MATCH_DATE(1800, "해당 날짜는 경기가 없습니다.");

    private final int errorCode;
    private final String errorMsg;
}
