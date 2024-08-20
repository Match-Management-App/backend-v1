package com.match_management.demo.openApi.jsonMapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.match_management.demo.openApi.dto.KakaoInfo;
import com.match_management.demo.openApi.exception.json.JsonException;

public class JsonMapper {
    private final static ObjectMapper MAPPER = new ObjectMapper();

    public static KakaoInfo mapping(final String jsonBody) {
        try {
            return MAPPER.readValue(jsonBody, KakaoInfo.class);
        } catch (JsonProcessingException e) {
            throw new JsonException.DeserializeException();
        }
    }
}
