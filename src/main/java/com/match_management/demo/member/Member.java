package com.match_management.demo.member;

import com.match_management.demo.member.exception.MemberException;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long oauthId;
    private Long statId;
    private String name;
    private String position;
    private boolean authenticated;

    public Member(final Long oauthId, final String name, final String position) {
        this.oauthId = oauthId;
        this.name = name;
        this.position = position;
        this.authenticated = false;
    }

    public void setStatId(final Long statId) {
        this.statId = statId;
    }

    public void authenticateCustomCode(final String code) {
        //나중에 application.yml로 뺄예정
        if (!Objects.equals(code, "MonsterUnited")) {
            throw new MemberException.UnMatchedCodeException();
        }
        authenticated = true;
    }
}
