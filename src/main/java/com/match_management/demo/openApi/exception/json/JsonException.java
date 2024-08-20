package com.match_management.demo.openApi.exception.json;

import com.match_management.demo.exception.CustomException;

public class JsonException extends CustomException {
    public JsonException(final JsonErrorCode jsonErrorCode) {
        super(jsonErrorCode);
    }

    public static class DeserializeException extends JsonException {
        public DeserializeException() {
            super(JsonErrorCode.DESERIALIZE_FAIL);
        }
    }
}
