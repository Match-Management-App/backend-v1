package com.match_management.demo.member.exception;

import com.match_management.demo.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MemberErrorCode implements ErrorCode {
    UNMATCHED_CODE(1500, "회원 가입 코드가 맞지 일치하지 않습니다."),
    NO_USER(1501, "존재하지 않는 User 입니다");

    private final int errorCode;
    private final String errorMsg;
}
