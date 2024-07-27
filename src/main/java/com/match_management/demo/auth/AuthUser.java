package com.match_management.demo.auth;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AuthUser {
    private Long oauthId;
    private String name;

    @Builder
    public AuthUser(final Long oauthId, final String name) {
        this.oauthId = oauthId;
        this.name = name;
    }
}
