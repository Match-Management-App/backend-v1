package com.match_management.demo.openApi;

import com.match_management.demo.openApi.dto.KakaoInfo;
import com.match_management.demo.openApi.exception.KakaoApi.KakaoResponseErrorHandler;
import com.match_management.demo.openApi.jsonMapper.JsonMapper;
import java.net.URI;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class KakaoService {

    public KakaoInfo getUserInfo(final String accessToken) {

        return JsonMapper.mapping(
                this.getMethodRequest(accessToken)
        );
    }

    public String getMethodRequest(final String accessToken) {
        HttpHeaders header = new HttpHeaders();

        header.add(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);
        header.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE);

        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(header);

        URI uri = UriComponentsBuilder
                .fromHttpUrl("https://kapi.kakao.com/v2/user/me")
                .build()
                .toUri();


        final RestTemplate restTemplate = new RestTemplate();

        restTemplate.setErrorHandler(new KakaoResponseErrorHandler());
        return restTemplate
                .exchange(
                        uri.toString(),
                        HttpMethod.POST,
                        httpEntity,
                        String.class)
                .getBody();
    }

}
