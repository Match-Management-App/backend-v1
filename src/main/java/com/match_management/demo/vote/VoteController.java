package com.match_management.demo.vote;

import com.match_management.demo.auth.AuthUser;
import com.match_management.demo.auth.AuthUserInfo;
import com.match_management.demo.vote.dto.MonthlyAttendanceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/vote")
public class VoteController {
    private final VoteService voteService;

    @GetMapping("/attendece")
    public ResponseEntity<List<MonthlyAttendanceResponse>> monthlyAttendanceStates
            (@AuthUserInfo final AuthUser authUser,
             @RequestParam(name = "month") final int month
             )
    {
        return ResponseEntity
                .ok(
                        voteService.findAttendanceByMonth(authUser.getOauthId(), 2024, month)
                );
    }


}
