package com.match_management.demo.stat;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StatRepository extends JpaRepository<Stat, Long> {
    Optional<Stat> findByUserId(final Long userId);

    @Query("select s from Stat s where s.userId = :userId ORDER BY s.matchDate DESC")
    List<Stat> findTop4DECS(@Param(value = "userId") final Long userId, Pageable pageable);
}
