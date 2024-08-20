package com.match_management.demo.login.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SignUpRequest {
    private String accessToken;
    private String code;
    private String position;
}
