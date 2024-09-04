package com.match_management.demo.vote.swagger;

import com.match_management.demo.auth.AuthUser;
import com.match_management.demo.auth.AuthUserInfo;
import com.match_management.demo.vote.dto.MonthlyAttendanceResponse;
import com.match_management.demo.vote.dto.VoteMatchRequest;
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
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "vote", description = "경기 참석 투표에 관한 api")
public interface VoteApiDoc {

    @Operation(
            summary = "view matches that user attended",
            description = "최근 참석한 4경기를 반환하는 api",
            responses = {
                    @ApiResponse(responseCode = "200", description = "조회 성공",
                            content = @Content(schema = @Schema(implementation = MonthlyAttendanceResponse.class))),
                    @ApiResponse(responseCode = "404", description = "조회 실패")
            }
    )
    @GetMapping("/attendance")
    ResponseEntity<List<MonthlyAttendanceResponse>> monthlyAttendanceStates
            (@AuthUserInfo final AuthUser authUser,
             @RequestParam(name = "month") final int month
            );

    @Operation(
            summary = "vote match",
            description = "경기에 참, 불 상태를 투표하는 api",
            responses = {
                    @ApiResponse(responseCode = "200", description = "조회 성공"),
                    @ApiResponse(responseCode = "404", description = "조회 실패")
            }
    )
    @PostMapping("")
    ResponseEntity<HttpStatus> vote
            (@AuthUserInfo final AuthUser authUser,
             final VoteMatchRequest voteMatchRequest
            );
}
