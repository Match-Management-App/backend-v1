package com.match_management.demo.match;

import com.match_management.demo.auth.AuthUser;
import com.match_management.demo.auth.AuthUserInfo;
import com.match_management.demo.match.dto.MonthlyScheduleResponse;
import com.match_management.demo.match.dto.NextMatchResponse;
import com.match_management.demo.match.dto.SetNextMatchRequest;
import com.match_management.demo.match.swagger.MatchApiDoc;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/matches")
@RequiredArgsConstructor
public class MatchController implements MatchApiDoc {
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

    @PostMapping("")
    public ResponseEntity<String> setNextMatch(@RequestBody final SetNextMatchRequest setNextMatchRequest) {
        matchService.setMatch(setNextMatchRequest);
        return ResponseEntity.ok("OK");
    }
}
