package com.match_management.demo.vote.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MonthlyAttendanceResponse {
    private LocalDateTime matchDay;
    private boolean attendance;

    @Builder
    public MonthlyAttendanceResponse(final LocalDateTime matchDay, final boolean attendance) {
        this.matchDay = matchDay;
        this.attendance = attendance;
    }
}
