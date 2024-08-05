package com.match_management.demo.stat.dto;

import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.PriorityQueue;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecentlyStatResponse {
    private LocalDateTime date;
    private Integer goals;
    private Integer assist;
    private Integer defence;

    @Builder
    public RecentlyStatResponse(final LocalDateTime date, final int goals, final int assist, final int defence) {
        this.date = date;
        this.goals = goals;
        this.assist = assist;
        this.defence = defence;
    }

}
