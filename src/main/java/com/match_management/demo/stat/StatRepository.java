package com.match_management.demo.stat;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StatRepository extends JpaRepository<Stat, Long> {
    public Stat findByUserId(final Long userId);
}
