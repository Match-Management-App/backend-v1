package com.match_management.demo.match.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MonthlyScheduleResponse {
    private LocalDateTime matchDay;
    private String spot;

    @Builder
    public MonthlyScheduleResponse(final LocalDateTime matchDay, final String spot) {
        this.matchDay = matchDay;
        this.spot = spot;
    }
}
