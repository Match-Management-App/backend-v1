package com.match_management.demo.board;

import com.match_management.demo.auth.AuthUser;
import com.match_management.demo.auth.AuthUserInfo;
import com.match_management.demo.board.dto.*;
import com.match_management.demo.board.swagger.BoardControllerApiDoc;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/board")
public class BoardController implements BoardControllerApiDoc {
    private final BoardService boardService;

    @GetMapping("")
    public Page<BoardResponse> viewAllBoard
            (@AuthUserInfo final AuthUser authUser,
             @PageableDefault(page = 1) final Pageable pageable)
    {
        return boardService.viewAllBoard(pageable);
    }

    @PostMapping("")
    public ResponseEntity<BoardIdResponse> create
            (@AuthUserInfo final AuthUser authUser,
             @RequestBody final BoardCreateRequest boardCreateRequest)
    {
        return ResponseEntity.ok(
                boardService.create(
                        authUser.getOauthId(),
                        authUser.getName(),
                        boardCreateRequest.getTitle(),
                        boardCreateRequest.getMatchDate()
                )
        );
    }

    @PostMapping("/title")
    public ResponseEntity<BoardResponse> amendTitle
            (@AuthUserInfo final AuthUser authUser,
             @RequestBody final AmendTitleRequest amendTitleRequest)
    {
        return ResponseEntity.ok(
                boardService.amendTitle(
                        amendTitleRequest.getBoardId(),
                        amendTitleRequest.getTitle()
                )
        );
    }

    @PostMapping("/matchDate")
    public ResponseEntity<BoardResponse> amendMatchDate
            (@AuthUserInfo final AuthUser authUser,
             @RequestBody final AmendMatchDateRequest amendMatchDateRequest)
    {
        return ResponseEntity.ok(
                boardService.amendMatchDate(
                        amendMatchDateRequest.getBoardId(),
                        amendMatchDateRequest.getMatchDate())
        );
    }

    @DeleteMapping("")
    public ResponseEntity<BoardIdResponse> delete
            (@AuthUserInfo final AuthUser authUser,
             @RequestBody DeleteBoardRequest deleteBoardRequest)
    {
        return ResponseEntity.ok(
                boardService.delete(
                        deleteBoardRequest.getBoardId()
                )
        );
    }
}
