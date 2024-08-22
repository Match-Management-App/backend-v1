package com.match_management.demo.openApi.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
public class KakaoInfo {
    private Long id;
    private Properties properties;

    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    @ToString
    public static class Properties {
        private String nickname;
    }
}
