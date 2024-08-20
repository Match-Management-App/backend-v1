package com.match_management.demo.openApi.exception.KakaoApi;

import com.match_management.demo.exception.CustomException;

public class KakaoApiException extends CustomException {
    public KakaoApiException(final KakaoApiErrorCode kakaoApiErrorCode) {
        super(kakaoApiErrorCode);
    }

    public static class InvalidAccessToken extends KakaoApiException {
        public InvalidAccessToken() {
            super(KakaoApiErrorCode.INVALID_TOKEN);
        }
    }

    public static class InvalidDataType extends KakaoApiException {
        public InvalidDataType() {
            super(KakaoApiErrorCode.INVALID_DATA_TYPE);
        }
    }
}
