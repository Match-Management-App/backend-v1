package com.match_management.demo.login;

import com.match_management.demo.auth.jwt.JwtService;
import com.match_management.demo.cookie.CookieShop;
import com.match_management.demo.login.dto.SignUpRequest;
import com.match_management.demo.login.dto.SignUpResponse;
import com.match_management.demo.openApi.KakaoService;
import com.match_management.demo.openApi.dto.KakaoInfo;
import com.match_management.demo.user.User;
import com.match_management.demo.user.UserRepository;
import com.match_management.demo.user.UserService;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LoginService {
    private final UserService userService;
    private final UserRepository userRepository;
    private final KakaoService kakaoService;
    private final JwtService jwtService;

    @Transactional
    public SignUpResponse signUp(final HttpServletResponse response, final SignUpRequest signUpRequest) {
        if (!Objects.equals(signUpRequest.getCode(), "MonsterUnited")) {
            throw new RuntimeException();
        }
        //TODO 1 카카오 api에 정보 요청하기
        final KakaoInfo kakaoInfo = kakaoService.getUserInfo(signUpRequest.getAccessToken());

        //TODO 2 user 생성
        final User user = userRepository.findByOauthId(kakaoInfo.getId())
                .orElseGet(() -> userService.create(
                        kakaoInfo.getId(), kakaoInfo.getName(), signUpRequest.getPosition())
                );

        if (!user.isAuthenticated()) {
            user.authenticateCustomCode(signUpRequest.getCode());
        }

        //TODO 3 accessToken 생성
        final String accessToken = jwtService.createAccessToken(kakaoInfo.getId(), kakaoInfo.getName());

        //TODO 4 accessToken 과 refreshToken cookie에 httpOnly로 저장
        final String refreshToken = jwtService.createRefreshToken(kakaoInfo.getId(), kakaoInfo.getName());

        CookieShop.bake(response, 30 * 60, "accessToken", accessToken);
        CookieShop.bake(response, 14 * 24 * 60 * 60, "refreshToken", refreshToken);

        return SignUpResponse
                .builder()
                .accessToken(accessToken)
                .build();
    }
}
