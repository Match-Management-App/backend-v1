package com.match_management.demo.vote.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class VoteMatchRequest {
    private String matchDay;
    private Long boardId;
    private boolean isAttendance;
}
