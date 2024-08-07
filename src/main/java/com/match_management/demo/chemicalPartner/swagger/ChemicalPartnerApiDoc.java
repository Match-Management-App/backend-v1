package com.match_management.demo.chemicalPartner.swagger;

import com.match_management.demo.auth.AuthUser;
import com.match_management.demo.auth.AuthUserInfo;
import com.match_management.demo.chemicalPartner.dto.ChemicalResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@Tag(name = "chemical partner", description = "자신과 가장 합이 잘 맞는 유저를 찾는 api")
public interface ChemicalPartnerApiDoc {

    @Operation(
            summary = "get assist partners",
            description = "자신의 골에 가장 많은 어시스트를 한 유저 3명을 반환하는 api",
            responses = {
                    @ApiResponse(responseCode = "200", description = "조회 완료",
                            content = @Content(schema = @Schema(implementation = ChemicalResponse.class))),
                    @ApiResponse(responseCode = "404", description = "조회 실패")
            }
    )
    @GetMapping("/assists/best")
    ResponseEntity<ChemicalResponse> bestAssistsChemicalPartner(@AuthUserInfo final AuthUser authUser);

    @Operation(
            summary = "get goal partners",
            description = "자신이 가장 많은 어시스트를 해준 유저 3명을 반환하는 api",
            responses = {
                    @ApiResponse(responseCode = "200", description = "조회 완료",
                            content = @Content(schema = @Schema(implementation = ChemicalResponse.class))),
                    @ApiResponse(responseCode = "404", description = "조회 실패")
            }
    )
    @GetMapping("/goals/best")
    ResponseEntity<ChemicalResponse> bestGoalsChemicalPartner(@AuthUserInfo final AuthUser authUser);

    @Operation(
            summary = "get goal partners",
            description = "자신의 골에 가장 적은 어시스트를 해준 유저 3명을 반환하는 api",
            responses = {
                    @ApiResponse(responseCode = "200", description = "조회 완료",
                            content = @Content(schema = @Schema(implementation = ChemicalResponse.class))),
                    @ApiResponse(responseCode = "404", description = "조회 실패")
            }
    )
    @GetMapping("/assists/worst")
    ResponseEntity<ChemicalResponse> worstAssistsChemicalPartner(@AuthUserInfo AuthUser authUser);

    @Operation(
            summary = "get goal partners",
            description = "자신이 가장 적은 어시스트를 해준 유저 3명을 반환하는 api",
            responses = {
                    @ApiResponse(responseCode = "200", description = "조회 완료",
                            content = @Content(schema = @Schema(implementation = ChemicalResponse.class))),
                    @ApiResponse(responseCode = "404", description = "조회 실패")
            }
    )
    @GetMapping("/goals/worst")
    ResponseEntity<ChemicalResponse> worstGoalChemicalPartner(@AuthUserInfo AuthUser authUser);
}
