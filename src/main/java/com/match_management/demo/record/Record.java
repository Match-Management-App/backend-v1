package com.match_management.demo.record;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Record {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private Integer goal;
    private Integer assist;
    private Integer defence;
    private Integer attendance;

    @Builder
    public Record(final Long userId, final Integer goal, final Integer assist,
                  final Integer defence, final Integer attendance) {
        this.userId = userId;
        this.goal = goal;
        this.assist = assist;
        this.defence = defence;
        this.attendance = attendance;
    }

    public void accumulateGoalPoints(final int goals) {
        this.goal += goals;
    }

    public void accumulateAssistPoints(final int assists) {
        this.assist += assists;
    }

    public void accumulateAttendancePoints(final int attendance) {
        this.attendance += attendance;
    }

    public void accumulateDefencePoints(final int defences) {
        this.defence += defences;
    }
}
