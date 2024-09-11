package com.match_management.demo.match.swagger;

import com.match_management.demo.auth.AuthUser;
import com.match_management.demo.auth.AuthUserInfo;
import com.match_management.demo.match.dto.MonthlyScheduleResponse;
import com.match_management.demo.match.dto.NextMatchResponse;
import com.match_management.demo.match.dto.SetNextMatchRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "match", description = "경기 관련 정보 api")
public interface MatchApiDoc {

    @Operation(
            summary = "view next match",
            description = "다음 경기 정보를 반환하는 api",
            responses = {
                    @ApiResponse(responseCode = "200", description = "조회 완료",
                            content = @Content(schema = @Schema(implementation = NextMatchResponse.class))),
                    @ApiResponse(responseCode = "404", description = "조회 실패")
            }
    )
    @GetMapping("/schedule")
    ResponseEntity<NextMatchResponse> nextMatch(@AuthUserInfo final AuthUser authUser);

    @Operation(
            summary = "view matches in month",
            description = "알맞은 월에 있는 경기를 조회하여 반환하는 api",
            responses = {
                    @ApiResponse(responseCode = "200", description = "조회 완료",
                            content = @Content(schema = @Schema(implementation = MonthlyScheduleResponse.class))),
                    @ApiResponse(responseCode = "404", description = "조회 실패")
            }
    )
    @GetMapping("/month")
    ResponseEntity<List<MonthlyScheduleResponse>> monthlyMatches(@AuthUserInfo final AuthUser authUser,
                                                                        @RequestParam(name = "month") final int month);

    @PostMapping("")
    ResponseEntity<String> setNextMatch(final SetNextMatchRequest setNextMatchRequest);
}
