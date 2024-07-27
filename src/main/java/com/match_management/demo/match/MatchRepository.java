package com.match_management.demo.match;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long> {
    Optional<Match> findById(final Long id);

    @Query("select m from Match m where " +
            "function('YEAR', m.date) = :year AND FUNCTION('MONTH', m.date) = :month")
    List<Match> findByMonth(@Param(value = "year") final int year,
                            @Param(value = "month") final int month);
}
