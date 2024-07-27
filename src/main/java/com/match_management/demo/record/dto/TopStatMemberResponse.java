package com.match_management.demo.record.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TopStatMemberResponse {
    private String userName;
    private Integer stats;

    @Builder
    public TopStatMemberResponse(final String userName, final int stats) {
        this.userName = userName;
        this.stats = stats;
    }
}
