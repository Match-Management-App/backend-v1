package com.match_management.demo.record;

import com.match_management.demo.stat.Stat;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecordRepository extends JpaRepository<Record, Long> {
    Optional<Record> findByUserId(final Long userId);
    @Query("select r from Record r "
            + "ORDER BY r.goal DESC")
    List<Record> findByGoalPointsDESC(final Pageable pageable);

    @Query("select r from Record r "
            + "ORDER BY r.assist DESC")
    List<Record> findByAssistPointsDESC(final Pageable pageable);

    @Query("select r from Record r "
            + "ORDER BY r.defence DESC")
    List<Record> findByDefencePointsDESC(final Pageable pageable);

    @Query("select r from Record r "
            + "ORDER BY r.attendance DESC")
    List<Record> findByAttendancePointsDESC(final Pageable pageable);
}
