package com.match_management.demo.vote;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {
    List<Vote> findAllByBoardId(final Long boardId);

    @Query("select v from Vote v where v.boardId = :boardId and"
            + " v.attendance = true ")
    Optional<List<Vote>> findAllByBoardIdAndAttendanceIsTrue(@Param("boardId") final Long boardId);

    @Query("select v from Vote v where v.boardId = :boardId and"
            + " v.attendance = false ")
    Optional<List<Vote>> findAllByBoardIdAndAttendanceIsFalse(@Param("boardId") final Long boardId);

    @Query("select v from Vote v where v.userId = :userId AND FUNCTION('YEAR', v.matchDate) = :year"
            + " AND FUNCTION('MONTH', v.matchDate) = :month")
    List<Vote> findByUserIdMonthlyAttendance(@Param(value = "userId") final Long userId,
                                             @Param(value = "year") final int year,
                                             @Param(value = "month") final int month);
}
