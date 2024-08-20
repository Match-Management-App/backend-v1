package com.match_management.demo.login.swagger;

import com.match_management.demo.login.dto.SignUpRequest;
import com.match_management.demo.login.dto.SignUpResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "login", description = "로그인 관련 api")
public interface LoginApiDoc {
    @Operation(
            summary = "login in service",
            description = "로그인 하는 api",
            responses = {
                    @ApiResponse(responseCode = "200", description = "로그인 완료",
                            content = @Content(schema = @Schema(implementation = SignUpResponse.class))),
                    @ApiResponse(responseCode = "404", description = "로그인 실패")
            }

    )
    @PostMapping("")
    ResponseEntity<?> signUp(final HttpServletResponse response, @RequestBody final SignUpRequest signUpRequest);
}
