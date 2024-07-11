package com.match_management.demo.stat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Stat {
    @Id
    @GeneratedValue
    private Long id;

    private Long userId;

    private Long goalPoints;
    private Long assistPoints;
    private Long defencePoints;
    private Long attendancePoints;

    public Stat(final Long userId) {
        this.userId = userId;
        this.goalPoints = 0L;
        this.assistPoints = 0L;
        this.defencePoints = 0L;
        this.attendancePoints = 0L;
    }

    public void accumulateGoalPoints(final int goals) {
        this.goalPoints += goals;
    }

    public void accumulateAssistPoints(final int assists) {
        this.assistPoints += assists;
    }

    public void accumulateAttendancePoints(final int attendance) {
        this.attendancePoints += attendance;
    }

    public void accumulateDefencePoints(final int defences) {
        this.defencePoints += defences;
    }
}
