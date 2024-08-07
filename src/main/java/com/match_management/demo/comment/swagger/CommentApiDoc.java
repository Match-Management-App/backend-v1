package com.match_management.demo.comment.swagger;

import com.match_management.demo.auth.AuthUser;
import com.match_management.demo.auth.AuthUserInfo;
import com.match_management.demo.comment.dto.CommentRequest;
import com.match_management.demo.comment.dto.CommentsResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "comment", description = "게시판 댓글 관련 api")
public interface CommentApiDoc {

    @Operation(
            summary = "view all comments",
            description = "각 게시물에 달린 모든 댓글을 시간 오름차순으로 정렬하여 반환하는 api",
            responses = {
                    @ApiResponse(responseCode = "200", description = "조회 완료",
                            content = @Content(schema = @Schema(implementation = CommentsResponse.class))),
                    @ApiResponse(responseCode = "404", description = "조회 실패")
            }
    )
    @GetMapping("")
    ResponseEntity<List<CommentsResponse>> getComments
            (@AuthUserInfo final AuthUser authUser,
             @RequestParam(name = "boardId") final Long boardId);

    @Operation(
            summary = "leave a comment",
            description = "게시물에 댓글 달기",
            responses = {
                    @ApiResponse(responseCode = "200", description = "요청 완료")
            }
    )
    @PostMapping("")
    ResponseEntity<HttpStatus> leaveAComment
            (@AuthUserInfo final AuthUser authUser,
             @RequestBody final CommentRequest commentRequest);
}
