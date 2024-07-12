package com.match_management.demo.user;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "member")
public class User {
    @Id @GeneratedValue
    private Long id;

    private Long statId;
    private String name;
    private String position;

    public User(final String name, final String position) {
        this.name = name;
        this.position = position;
    }

    public void setStatId(final Long statId) {
        this.statId = statId;
    }

}
