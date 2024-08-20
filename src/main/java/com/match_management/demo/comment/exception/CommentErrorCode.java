package com.match_management.demo.comment.exception;

import com.match_management.demo.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CommentErrorCode implements ErrorCode {
    NO_COMMENT(1600, "존재하지 않는 comment 입니다");


    private final int errorCode;
    private final String errorMsg;
}
