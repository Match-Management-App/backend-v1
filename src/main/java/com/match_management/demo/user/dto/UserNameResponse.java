package com.match_management.demo.user.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserNameResponse {
    private String userName;

    @Builder
    public UserNameResponse(final String userName) {
        this.userName = userName;
    }
}
