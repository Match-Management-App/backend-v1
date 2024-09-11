package com.match_management.demo.exception;

import com.match_management.demo.comment.exception.CommentException;
import com.match_management.demo.openApi.exception.KakaoApi.KakaoApiException;
import com.match_management.demo.openApi.exception.json.JsonException;
import com.match_management.demo.record.exception.RecordException;
import com.match_management.demo.member.exception.MemberException;
import com.match_management.demo.member.exception.MemberException.NoMemberException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ExceptionHandleController {
    @ExceptionHandler(
            {
                    NoMemberException.class, CommentException.NoCommentException.class,
                    RecordException.NoRecordException.class
            }
    )
    public ResponseEntity<String> handleNoResultException(final CustomException e) {
        log.error(e.toString(), e);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.toString());
    }

    @ExceptionHandler(
            {
                    JsonException.DeserializeException.class, KakaoApiException.class,
                    MemberException.UnMatchedCodeException.class
            }
    )
    public ResponseEntity<String> handleBadRequestException(final CustomException e) {
        log.error(e.toString(), e);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.toString());
    }


}
