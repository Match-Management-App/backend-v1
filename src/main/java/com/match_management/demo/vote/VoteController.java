package com.match_management.demo.vote;

import com.match_management.demo.auth.AuthUser;
import com.match_management.demo.auth.AuthUserInfo;
import com.match_management.demo.vote.dto.MonthlyAttendanceResponse;
import com.match_management.demo.vote.dto.VoteMatchRequest;
import com.match_management.demo.vote.swagger.VoteApiDoc;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/vote")
public class VoteController implements VoteApiDoc {
    private final VoteService voteService;

    @GetMapping("/attendance")
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

    @PostMapping("")
    public ResponseEntity<HttpStatus> vote
            (@AuthUserInfo final AuthUser authUser,
             final VoteMatchRequest voteMatchRequest
            )
    {
        voteService.create(authUser.getOauthId(), voteMatchRequest.getBoardId(), voteMatchRequest.isAttendance());
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
