package com.match_management.demo.match;

import com.match_management.demo.auth.AuthUser;
import com.match_management.demo.auth.AuthUserInfo;
import com.match_management.demo.match.dto.MonthlyScheduleResponse;
import com.match_management.demo.match.dto.NextMatchResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/matches")
@RequiredArgsConstructor
public class MatchController {
    private final MatchService matchService;

    @GetMapping("/schedule")
    public ResponseEntity<NextMatchResponse> nextMatch(@AuthUserInfo final AuthUser authUser) {
        return ResponseEntity
                .ok(
                        matchService.findNextMatch()
                );
    }

    @GetMapping("/month")
    public ResponseEntity<List<MonthlyScheduleResponse>> monthlyMatches(@AuthUserInfo final AuthUser authUser,
                                                                        @RequestParam(name = "month") final int month) {
        return ResponseEntity
                .ok(
                        matchService.findSchedulesByMonth(2024, month)
                );
    }
}
