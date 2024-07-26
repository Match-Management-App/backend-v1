package com.match_management.demo.vote;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Vote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private Long boardId;
    private boolean attendance;

    public Vote(final Long userId, final Long boardId, final boolean isAttendance) {
        this.userId = userId;
        this.boardId = boardId;
        this.attendance = isAttendance;
    }

    public void changeVoted(final boolean attendance) {
        this.attendance = attendance;
    }
}
