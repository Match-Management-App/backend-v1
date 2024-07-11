package com.match_management.demo.stat;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StatService {
    final private StatRepository statRepository;

    @Transactional
    public Long initStat(final Long userId) {
        final Stat stat = new Stat(userId);

        statRepository.save(stat);
        return stat.getId();
    }

    public Stat findOne(final Long userId) {
        return statRepository.findByUserId(userId);
    }
}
