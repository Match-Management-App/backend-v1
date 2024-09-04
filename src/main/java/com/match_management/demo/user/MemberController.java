package com.match_management.demo.user;

import com.match_management.demo.auth.AuthUser;
import com.match_management.demo.auth.AuthUserInfo;
import com.match_management.demo.user.dto.MemberNameResponse;
import com.match_management.demo.user.swagger.MemberApiDoc;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/users")
public class MemberController implements MemberApiDoc {
    private final MemberService memberService;

    @GetMapping("/me")
    public ResponseEntity<MemberNameResponse> getUserName(@AuthUserInfo final AuthUser authUser) {
        return ResponseEntity.ok(
                MemberNameResponse
                        .builder()
                        .userName(authUser.getName())
                        .build()
        );
    }

    @DeleteMapping("/")
    public ResponseEntity<String> delete(@AuthUserInfo final AuthUser authUser) {
        memberService.delete(authUser);

        return ResponseEntity.ok("삭제 완료");
    }
}
