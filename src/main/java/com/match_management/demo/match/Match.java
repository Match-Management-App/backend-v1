package com.match_management.demo.match;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime date;
    private String spot;
    private int attendance;

    @Builder
    public Match(final int year, final int month, final int day, final int hour,
                 final String spot, final int attendance) {
        this.date = LocalDateTime.of(year, month, day, hour, 0);
        this.spot = spot;
        this.attendance = attendance;
    }

    public void setAttendance(final int attendance) {
        this.attendance = attendance;
    }
}
