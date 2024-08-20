package com.match_management.demo.login;

import com.match_management.demo.login.dto.SignUpRequest;
import com.match_management.demo.login.dto.SignUpResponse;
import com.match_management.demo.openApi.KakaoService;
import com.match_management.demo.openApi.dto.KakaoInfo;
import com.match_management.demo.user.UserService;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LoginService {
    private static UserService userService;
    private static KakaoService kakaoService;
    @Transactional
    public SignUpResponse signUp(final SignUpRequest signUpRequest) {
        if (!Objects.equals(signUpRequest.getCode(), "MonsterUnited")) {
            throw new RuntimeException();
        }
        //TODO 1 카카오 api에 정보 요청하기
        final KakaoInfo kakaoInfo = kakaoService.getUserInfo(signUpRequest.getAccessToken());
        //TODO 2 user 생성
        userService.create(kakaoInfo.getId(), kakaoInfo.getName(), signUpRequest.getPosition());
        //TODO 3 jwt 생성

        //TODO 4 refreshToken cookie에 httpOnly로 저장

        return SignUpResponse.builder().build();
    }
}
