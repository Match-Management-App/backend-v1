package com.match_management.demo.user.swagger;

import com.match_management.demo.auth.AuthUser;
import com.match_management.demo.auth.AuthUserInfo;
import com.match_management.demo.user.dto.MemberNameResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

@Tag(name = "user", description = "회원 정보에 관한 api")
public interface MemberApiDoc {

    @Operation(
            summary = "view user name",
            description = "유저 이름 반환하는 api",
            responses = {
                    @ApiResponse(responseCode = "200", description = "조회 성공",
                            content = @Content(schema = @Schema(implementation = MemberNameResponse.class))),
                    @ApiResponse(responseCode = "404", description = "조회 실패")
            }
    )
    @GetMapping("/me")
    ResponseEntity<MemberNameResponse> getUserName(@AuthUserInfo final AuthUser authUser);

    @Operation(
            summary = "delete user api",
            description = "유저를 삭제하는 api",
            responses = {
                    @ApiResponse(responseCode = "200", description = "삭제 성공")
            }
    )
    @DeleteMapping("/")
    ResponseEntity<String> delete(@AuthUserInfo final AuthUser authUser);
}
