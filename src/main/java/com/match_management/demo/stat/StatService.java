package com.match_management.demo.stat;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StatService {
    final private StatRepository statRepository;

    @Transactional
    public Long initStat(final Long userId) {
        final Stat stat = new Stat(userId);

        statRepository.save(stat);
        return stat.getId();
    }

    public Stat findOne(final Long userId) {
        return statRepository.findByUserId(userId).orElseThrow(RuntimeException::new);
    }

    @Transactional
    public void accumulateGoalsStat(final Long userId, final int goalPoints) {
        final Stat stat = statRepository.findByUserId(userId)
                .orElseThrow(RuntimeException::new);

        stat.accumulateGoalPoints(goalPoints);
    }

    @Transactional
    public void accumulateAssistsStat(final Long userId, final int assistPoints) {
        final Stat stat = statRepository.findByUserId(userId)
                .orElseThrow(RuntimeException::new);

        stat.accumulateAssistPoints(assistPoints);
    }

    @Transactional
    public void accumulateAttendanceStat(final Long userId, final int attendancePoints) {
        final Stat stat = statRepository.findByUserId(userId)
                .orElseThrow(RuntimeException::new);

        stat.accumulateAttendancePoints(attendancePoints);
    }

    @Transactional
    public void accumulateDefencesStat(final Long userId, final int defencePoints) {
        final Stat stat = statRepository.findByUserId(userId)
                .orElseThrow(RuntimeException::new);

        stat.accumulateDefencePoints(defencePoints);
    }
}
