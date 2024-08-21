package com.match_management.demo.auth.jwt.swagger;

import com.match_management.demo.auth.AuthUser;
import com.match_management.demo.auth.AuthUserInfo;
import com.match_management.demo.auth.jwt.dto.ReissueRequest;
import com.match_management.demo.auth.jwt.dto.ReissueResponse;
import com.match_management.demo.board.dto.BoardResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "jwt", description = "jwt 관련 api")
public interface JwtApiDoc {
    @Operation(
            summary = "reissue jwt access token",
            description = "jwt 가 만료되었을 경우, 재발급 요청을 위한 api",
            responses = {
                    @ApiResponse(responseCode = "200", description = "발급 성공",
                            content = @Content(schema = @Schema(implementation = ReissueResponse.class))),
                    @ApiResponse(responseCode = "401", description = "발급 실패")
            }

    )
    @PostMapping("/reissue")
    ResponseEntity<ReissueResponse> reissue(
            final HttpServletResponse response,
            @RequestBody final ReissueRequest reissueRequest,
            @AuthUserInfo final AuthUser authUser,
            @CookieValue(value = "accessToken", defaultValue = "NONE") final String refreshToken,
            @CookieValue(value = "refreshToken", defaultValue = "NONE") final String accessToken
    );
}
