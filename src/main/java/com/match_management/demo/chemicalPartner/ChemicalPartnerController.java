package com.match_management.demo.chemicalPartner;

import com.match_management.demo.auth.AuthUser;
import com.match_management.demo.auth.AuthUserInfo;
import com.match_management.demo.chemicalPartner.dto.ChemicalResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/chemical")
public class ChemicalPartnerController {
    private final ChemicalPartnerService chemicalPartnerService;

    @GetMapping("/assists/best")
    public ResponseEntity<ChemicalResponse> bestAssistsChemicalPartner(@AuthUserInfo final AuthUser authUser) {
        return ResponseEntity
                .ok(
                        chemicalPartnerService.getBestAssistPartner(authUser.getOauthId())
                );
    }

    @GetMapping("/goals/best")
    public ResponseEntity<ChemicalResponse> bestGoalsChemicalPartner(@AuthUserInfo final AuthUser authUser) {
        return ResponseEntity
                .ok(
                        chemicalPartnerService.getBestGoalPartner(authUser.getOauthId())
                );
    }

    @GetMapping("/assists/worst")
    public ResponseEntity<ChemicalResponse> worstAssistsChemicalPartner(@AuthUserInfo AuthUser authUser) {
        return ResponseEntity
                .ok(
                        chemicalPartnerService.getWorstAssistPartner(authUser.getOauthId())
                );
    }

    @GetMapping("/goals/worst")
    public ResponseEntity<ChemicalResponse> worstGoalChemicalPartner(@AuthUserInfo AuthUser authUser) {
        return ResponseEntity
                .ok(
                        chemicalPartnerService.getWorstGoalPartner(authUser.getOauthId())
                );
    }
}
