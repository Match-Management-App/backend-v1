package com.match_management.demo.stat;

import com.match_management.demo.auth.AuthUser;
import com.match_management.demo.auth.AuthUserInfo;
import com.match_management.demo.stat.dto.RecentlyStatResponse;
import com.match_management.demo.stat.dto.RegisterStatRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/stat")
public class StatController {
    private final StatService statService;

    @GetMapping("/recently-record")
    public ResponseEntity<List<RecentlyStatResponse>> recentlyStats(@AuthUserInfo final AuthUser authUser) {
        return ResponseEntity
                .ok(
                        statService.getStats(authUser)
                );
    }

    @PostMapping("")
    public ResponseEntity<HttpStatus> registerStat
            (@AuthUserInfo final AuthUser authUser,
             @RequestBody final RegisterStatRequest registerStatRequest)
    {
        statService.create(
                authUser.getOauthId(),
                registerStatRequest.getMatchDate(),
                registerStatRequest.getGoals(),
                registerStatRequest.getAssist(),
                registerStatRequest.getDefence()
        );

        return ResponseEntity.ok(HttpStatus.OK);
    }
}
