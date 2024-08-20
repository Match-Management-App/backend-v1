package com.match_management.demo.openApi.exception.KakaoApi;

import java.io.IOException;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

public class KakaoResponseErrorHandler implements ResponseErrorHandler {
    @Override
    public boolean hasError(final ClientHttpResponse response) throws IOException {
        return response.getStatusCode().is4xxClientError();
    }

    @Override
    public void handleError(final ClientHttpResponse response) throws IOException {
        if (response.getStatusCode() == HttpStatus.BAD_REQUEST) {
            throw new KakaoApiException.InvalidDataType();
        }
        if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
            throw new KakaoApiException.InvalidAccessToken();
        }
    }
}
