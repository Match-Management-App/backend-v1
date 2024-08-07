package com.match_management.demo.stat.swagger;

import com.match_management.demo.auth.AuthUser;
import com.match_management.demo.auth.AuthUserInfo;
import com.match_management.demo.stat.dto.RecentlyStatResponse;
import com.match_management.demo.stat.dto.RegisterStatRequest;
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

@Tag(name = "stat", description = "경기 당 기록을 조회에 대한 api")
public interface StatApiDoc {

    @Operation(
            summary = "view recently record about 4 matches",
            description = "4경기 당 기록을 보여주는 api",
            responses = {
                    @ApiResponse(responseCode = "200", description = "조회 성공",
                            content = @Content(schema = @Schema(implementation = RecentlyStatResponse.class))),
                    @ApiResponse(responseCode = "404", description = "조회 실패")
            }
    )
    @GetMapping("/recently-record")
    ResponseEntity<List<RecentlyStatResponse>> recentlyStats(@AuthUserInfo final AuthUser authUser);

    @Operation(
            summary = "register stat",
            description = "경기 후 스텟 등록",
            responses = {
                    @ApiResponse(responseCode = "200", description = "요청 성공"),
                    @ApiResponse(responseCode = "404", description = "요청 실패")
            }
    )
    @PostMapping("")
    ResponseEntity<HttpStatus> registerStat
            (@AuthUserInfo final AuthUser authUser,
             @RequestBody final RegisterStatRequest registerStatRequest);
}
