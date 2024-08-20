package com.match_management.demo.login;

import com.match_management.demo.login.dto.SignUpRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/login")
public class LoginController {
    private final LoginService loginService;

    @PostMapping("")
    public ResponseEntity<?> signUp(@RequestBody final SignUpRequest signUpRequest) {
        return ResponseEntity.ok(
                loginService.signUp(signUpRequest)
        );
    }
}
