package com.match_management.demo.stat;

import com.match_management.demo.auth.AuthUser;
import com.match_management.demo.auth.AuthUserInfo;
import com.match_management.demo.stat.dto.RecentlyStatResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
