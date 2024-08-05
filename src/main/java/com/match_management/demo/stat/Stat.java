package com.match_management.demo.stat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Stat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private Integer goal;
    private Integer assist;
    private Integer defence;
    private LocalDateTime matchDate;

    @Builder
    public Stat(final Long userId, final int goal, final int assist,
                final int defence, final LocalDateTime matchDate) {
        this.userId = userId;
        this.goal = goal;
        this.assist = assist;
        this.defence = defence;
        this.matchDate = matchDate;
    }
}
