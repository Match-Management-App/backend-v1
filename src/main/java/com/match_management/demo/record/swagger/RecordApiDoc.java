package com.match_management.demo.record.swagger;

import com.match_management.demo.auth.AuthUser;
import com.match_management.demo.auth.AuthUserInfo;
import com.match_management.demo.record.dto.TopStatMemberResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@Tag(name = "record", description = "모든 유저의 스텟 기록에 관한 api")
public interface RecordApiDoc {

    @Operation(
            summary = "view Top3 scorer",
            description = "득점자 top 3를 반환 하는 api",
            responses = {
                    @ApiResponse(responseCode = "200", description = "조회 완료",
                            content = @Content(schema = @Schema(implementation = TopStatMemberResponse.class))),
                    @ApiResponse(responseCode = "404", description = "조회 실패")
            }
    )
    @GetMapping("/goals/hall-of-fame")
    ResponseEntity<List<TopStatMemberResponse>> hallOfFameScorerList(@AuthUserInfo AuthUser authUser);

    @Operation(
            summary = "view Top3 assist user",
            description = "어시스트 최다 top 3를 반환 하는 api",
            responses = {
                    @ApiResponse(responseCode = "200", description = "조회 완료",
                            content = @Content(schema = @Schema(implementation = TopStatMemberResponse.class)))
            }
    )
    @GetMapping("/assists/hall-of-fame")
    ResponseEntity<List<TopStatMemberResponse>> hallOfFameAssistsList(@AuthUserInfo AuthUser authUser);

    @Operation(
            summary = "view Top3 defence user",
            description = "수비스텟 최다 top 3를 반환 하는 api",
            responses = {
                    @ApiResponse(responseCode = "200", description = "조회 완료",
                            content = @Content(schema = @Schema(implementation = TopStatMemberResponse.class)))
            }
    )
    @GetMapping("/defences/hall-of-fame")
    ResponseEntity<List<TopStatMemberResponse>> hallOfFameDefencesList(@AuthUserInfo AuthUser authUser);

    @Operation(
            summary = "view Top3 attendance user",
            description = "출석 최다 top 3를 반환 하는 api",
            responses = {
                    @ApiResponse(responseCode = "200", description = "조회 완료",
                            content = @Content(schema = @Schema(implementation = TopStatMemberResponse.class)))
            }
    )
    @GetMapping("/attendance/hall-of-fame")
    ResponseEntity<List<TopStatMemberResponse>> hallOfFameAttendanceList(@AuthUserInfo AuthUser authUser);
}
