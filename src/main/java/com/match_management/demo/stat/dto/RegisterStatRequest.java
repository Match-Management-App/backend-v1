package com.match_management.demo.stat.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RegisterStatRequest {
    private int goals;
    private int assist;
    private int defence;
    private String matchDate;
}
