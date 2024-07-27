package com.match_management.demo.record;

import com.match_management.demo.auth.AuthUser;
import com.match_management.demo.auth.AuthUserInfo;
import com.match_management.demo.record.dto.TopStatMemberResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/record")
public class RecordController {
    private final RecordService recordService;

    @GetMapping("/goals/hall-of-fame")
    public ResponseEntity<List<TopStatMemberResponse>> hallOfFameScorerList(@AuthUserInfo AuthUser authUser) {
        return ResponseEntity
                .ok(
                        recordService.getTopGoalMembers()
                );
    }

    @GetMapping("/assists/hall-of-fame")
    public ResponseEntity<List<TopStatMemberResponse>> hallOfFameAssistsList(@AuthUserInfo AuthUser authUser) {
        return ResponseEntity
                .ok(
                        recordService.getTopAssistMembers()
                );
    }

    @GetMapping("/defences/hall-of-fame")
    public ResponseEntity<List<TopStatMemberResponse>> hallOfFameDefencesList(@AuthUserInfo AuthUser authUser) {
        return ResponseEntity
                .ok(
                        recordService.getTopDefenceMembers()
                );
    }

    @GetMapping("/attendance/hall-of-fame")
    public ResponseEntity<List<TopStatMemberResponse>> hallOfFameAttendanceList(@AuthUserInfo AuthUser authUser) {
        return ResponseEntity
                .ok(
                        recordService.getTopAttendanceMembers()
                );
    }
}
