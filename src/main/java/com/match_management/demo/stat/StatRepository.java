package com.match_management.demo.stat;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StatRepository extends JpaRepository<Stat, Long> {
    Optional<Stat> findByUserId(final Long userId);

    @Query("select s from Stat s "
            + "ORDER BY s.goalPoints DESC")
    List<Stat> findByGoalPointsDESC(final Pageable pageable);

    @Query("select s from Stat s "
            + "ORDER BY s.assistPoints DESC")
    List<Stat> findByAssistPointsDESC(final Pageable pageable);

    @Query("select s from Stat s "
            + "ORDER BY s.defencePoints DESC")
    List<Stat> findByDefencePointsDESC(final Pageable pageable);

    @Query("select s from Stat s "
            + "ORDER BY s.attendancePoints DESC")
    List<Stat> findByAttendancePointsDESC(final Pageable pageable);
}
