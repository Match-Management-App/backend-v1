package com.match_management.demo.stat;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatRepository extends JpaRepository<Stat, Long> {
    Optional<Stat> findByUserId(final Long userId);
}
