package com.match_management.demo.vote;

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
public class Vote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private Long matchId;
    private LocalDateTime matchDate;
    private boolean attendance;

    @Builder
    public Vote(final Long userId, final Long matchId,
                final LocalDateTime date, final boolean isAttendance) {
        this.userId = userId;
        this.matchId = matchId;
        this.matchDate = date;
        this.attendance = isAttendance;
    }

    public void changeVoted(final boolean attendance) {
        this.attendance = attendance;
    }
}
