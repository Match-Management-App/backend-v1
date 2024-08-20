package com.match_management.demo.openApi.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
public class KakaoInfo {
    private Long id;
    private String name;
}
