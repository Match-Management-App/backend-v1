package com.match_management.demo.user;

import com.match_management.demo.auth.AuthUser;
import com.match_management.demo.auth.AuthUserInfo;
import com.match_management.demo.user.dto.UserNameResponse;
import com.match_management.demo.user.swagger.UserApiDoc;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/users")
public class UserController implements UserApiDoc {

    @GetMapping("/me")
    public ResponseEntity<UserNameResponse> getUserName(@AuthUserInfo final AuthUser authUser) {
        return ResponseEntity.ok(
                UserNameResponse
                        .builder()
                        .userName(authUser.getName())
                        .build()
        );
    }


}
