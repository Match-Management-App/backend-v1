package com.match_management.demo.match.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NextMatchResponse {
    private LocalDateTime matchDay;
    private Integer participants;
    private String spot;

    @Builder
    public NextMatchResponse(final LocalDateTime matchDay, final int participants, final String spot) {
        this.matchDay = matchDay;
        this.participants = participants;
        this.spot = spot;
    }
}
