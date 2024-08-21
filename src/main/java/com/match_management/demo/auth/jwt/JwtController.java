package com.match_management.demo.auth.jwt;

import com.match_management.demo.auth.AuthUser;
import com.match_management.demo.auth.AuthUserInfo;
import com.match_management.demo.auth.jwt.dto.ReissueRequest;
import com.match_management.demo.auth.jwt.dto.ReissueResponse;
import com.match_management.demo.auth.jwt.swagger.JwtApiDoc;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/jwt")
@RequiredArgsConstructor
public class JwtController implements JwtApiDoc {

    private final JwtService jwtService;

    @PostMapping("/reissue")
    public ResponseEntity<ReissueResponse> reissue(
            final HttpServletResponse response,
            @RequestBody final ReissueRequest reissueRequest,
            @AuthUserInfo final AuthUser authUser,
            @CookieValue(value = "accessToken", defaultValue = "NONE") final String accessToken,
            @CookieValue(value = "refreshToken", defaultValue = "NONE") final String refreshToken
    )
    {
        return ResponseEntity.ok(
                jwtService.reissue(response, reissueRequest, authUser, accessToken, refreshToken)
        );
    }
}
