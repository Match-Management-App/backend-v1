package com.match_management.demo.stat;

import com.match_management.demo.user.User;
import com.match_management.demo.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StatService {
    final private StatRepository statRepository;
    final private UserRepository userRepository;

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

    public List<String> getTop3GoalMembers() {
        final List<Stat> stats = statRepository.findByGoalPointsDESC(PageRequest.of(0, 3));

        return stats.stream()
                .map(s -> {
                    final User user = userRepository.findById(s.getUserId())
                            .orElseThrow(RuntimeException::new);
                    return user.getName();
                })
                .toList();
    }

    public List<String> getTopAssistMembers() {
        final List<Stat> stats = statRepository.findByAssistPointsDESC(PageRequest.of(0, 3));

        return stats.stream()
                .map(s -> {
                    final User user = userRepository.findById(s.getUserId())
                            .orElseThrow(RuntimeException::new);
                    return user.getName();
                })
                .toList();
    }

    public List<String> getTopDefenceMembers() {
        final List<Stat> stats = statRepository.findByDefencePointsDESC(PageRequest.of(0, 3));

        return stats.stream()
                .map(s -> {
                    final User user = userRepository.findById(s.getUserId())
                            .orElseThrow(RuntimeException::new);
                    return user.getName();
                })
                .toList();
    }
    public List<String> getTopAttendanceMembers() {
        final List<Stat> stats = statRepository.findByAttendancePointsDESC(PageRequest.of(0, 3));

        return stats.stream()
                .map(s -> {
                    final User user = userRepository.findById(s.getUserId())
                            .orElseThrow(RuntimeException::new);
                    return user.getName();
                })
                .toList();
    }
}
