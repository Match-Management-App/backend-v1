package com.match_management.demo.board.swagger;

import com.match_management.demo.auth.AuthUser;
import com.match_management.demo.auth.AuthUserInfo;
import com.match_management.demo.board.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "board", description = "게시판 조회 및 수정에 관한 api")
public interface BoardControllerApiDoc {

    @Operation(
            summary = "view all board using page",
            description = "작성된 모든 게시판을 최근 순으로 정렬하여 page로 조회하는 api",
            responses = {
                    @ApiResponse(responseCode = "200", description = "조회 성공",
                            content = @Content(schema = @Schema(implementation = BoardResponse.class))),
                    @ApiResponse(responseCode = "404", description = "조회 실패")
            }
    )
    @GetMapping("")
    Page<BoardResponse> viewAllBoard
            (@AuthUserInfo final AuthUser authUser,
             @PageableDefault(page = 1) final Pageable pageable);

    @Operation(
            summary = "create board",
            description = "게시판을 작성하는 api",
            responses = {
                    @ApiResponse(responseCode = "200", description = "요청 성공",
                            content = @Content(schema = @Schema(implementation = BoardIdResponse.class))),
                    @ApiResponse(responseCode = "404", description = "요청 실패")
            }
    )
    @PostMapping("")
    ResponseEntity<BoardIdResponse> create
            (@AuthUserInfo final AuthUser authUser,
             @RequestBody final BoardCreateRequest boardCreateRequest);

    @Operation(
            summary = "amend title of board",
            description = "게시판 제목을 수정하는 api",
            responses = {
                    @ApiResponse(responseCode = "200", description = "요청 성공",
                            content = @Content(schema = @Schema(implementation = BoardResponse.class))),
                    @ApiResponse(responseCode = "404", description = "요청 실패")
            }
    )
    @PostMapping("/title")
    ResponseEntity<BoardResponse> amendTitle
            (@AuthUserInfo final AuthUser authUser,
             @RequestBody final AmendTitleRequest amendTitleRequest);

    @Operation(
            summary = "amend match date of board",
            description = "게시판의 경기 날짜를 수정하는 api",
            responses = {
                    @ApiResponse(responseCode = "200", description = "요청 성공",
                            content = @Content(schema = @Schema(implementation = BoardResponse.class))),
                    @ApiResponse(responseCode = "404", description = "요청 실패")
            }
    )
    @PostMapping("/matchDate")
    ResponseEntity<BoardResponse> amendMatchDate
            (@AuthUserInfo final AuthUser authUser,
             @RequestBody final AmendMatchDateRequest amendMatchDateRequest);

    @Operation(
            summary = "delete board",
            description = "게시판을 삭제하는 api",
            responses = {
                    @ApiResponse(responseCode = "200", description = "삭제 성공",
                            content = @Content(schema = @Schema(implementation = BoardIdResponse.class))),
                    @ApiResponse(responseCode = "404", description = "요청 실패")
            }
    )
    @DeleteMapping("")
    ResponseEntity<BoardIdResponse> delete
            (@AuthUserInfo final AuthUser authUser,
             @RequestBody DeleteBoardRequest deleteBoardRequest);
}
