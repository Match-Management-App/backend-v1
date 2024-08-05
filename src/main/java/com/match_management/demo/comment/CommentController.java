package com.match_management.demo.comment;

import com.match_management.demo.auth.AuthUser;
import com.match_management.demo.auth.AuthUserInfo;
import com.match_management.demo.comment.dto.CommentRequest;
import com.match_management.demo.comment.dto.CommentsResponse;
import com.match_management.demo.comment.swagger.CommentApiDoc;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/comment")
public class CommentController implements CommentApiDoc {
    private final CommentService commentService;

    @GetMapping("")
    public ResponseEntity<List<CommentsResponse>> getComments
            (@AuthUserInfo final AuthUser authUser,
             @RequestParam(name = "boardId") final Long boardId)
    {
        return ResponseEntity
                .ok(
                        commentService.viewAllComments(boardId)
                );
    }

    @PostMapping("")
    public ResponseEntity<HttpStatus> leaveAComment
            (@AuthUserInfo final AuthUser authUser,
             @RequestBody final CommentRequest commentRequest)
    {
        commentService.create(authUser.getOauthId(), commentRequest.getBoarId(), commentRequest.getText());

        return ResponseEntity.ok(HttpStatus.OK);
    }

    //TODO 댓글 수정도 만들자
}
