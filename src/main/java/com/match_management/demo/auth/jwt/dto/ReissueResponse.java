package com.match_management.demo.auth.jwt.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReissueResponse {
    private String accessToken;

    @Builder
    public ReissueResponse(final String accessToken) {
        this.accessToken = accessToken;
    }
}
