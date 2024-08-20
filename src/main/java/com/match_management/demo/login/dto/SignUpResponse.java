package com.match_management.demo.login.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SignUpResponse {
    private String accessToken;

    @Builder
    public SignUpResponse(final String accessToken) {
        this.accessToken = accessToken;
    }
}
