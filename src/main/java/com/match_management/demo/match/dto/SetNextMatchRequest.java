package com.match_management.demo.match.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SetNextMatchRequest {
    private String nextMatch;
    private int hour;
    private String spot;
}
