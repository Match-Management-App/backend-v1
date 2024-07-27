package com.match_management.demo.record;

import com.match_management.demo.record.dto.TopStatMemberResponse;
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
public class RecordService {

    private final RecordRepository recordRepository;
    private final UserRepository userRepository;

    @Transactional
    public Long initStat(final Long userId) {
        final Record record = Record.builder().userId(userId).build();

        recordRepository.save(record);
        return record.getId();
    }
    @Transactional
    public void accumulateGoalsStat(final Long userId, final int goalPoints) {
        final Record record = recordRepository.findByUserId(userId)
                .orElseThrow(RuntimeException::new);

        record.accumulateGoalPoints(goalPoints);
    }

    @Transactional
    public void accumulateAssistsStat(final Long userId, final int assistPoints) {
        final Record record = recordRepository.findByUserId(userId)
                .orElseThrow(RuntimeException::new);

        record.accumulateAssistPoints(assistPoints);
    }

    @Transactional
    public void accumulateAttendanceStat(final Long userId, final int attendancePoints) {
        final Record record = recordRepository.findByUserId(userId)
                .orElseThrow(RuntimeException::new);

        record.accumulateAttendancePoints(attendancePoints);
    }

    @Transactional
    public void accumulateDefencesStat(final Long userId, final int defencePoints) {
        final Record record = recordRepository.findByUserId(userId)
                .orElseThrow(RuntimeException::new);

        record.accumulateDefencePoints(defencePoints);
    }

    public List<TopStatMemberResponse> getTopGoalMembers() {
        final List<Record> records = recordRepository.findByGoalPointsDESC(PageRequest.of(0, 3));

        return records.stream()
                .map(s -> TopStatMemberResponse
                        .builder()
                        .userName(
                                userRepository
                                        .findById(s.getUserId())
                                        .orElseThrow(RuntimeException::new)
                                        .getName()
                        )
                        .stats(s.getGoal())
                        .build()
                )
                .toList();
    }

    public List<TopStatMemberResponse> getTopAssistMembers() {
        final List<Record> records = recordRepository.findByAssistPointsDESC(PageRequest.of(0, 3));

        return records.stream()
                .map(s -> TopStatMemberResponse
                        .builder()
                        .userName(
                                userRepository
                                        .findById(s.getUserId())
                                        .orElseThrow(RuntimeException::new)
                                        .getName()
                        )
                        .stats(s.getAssist())
                        .build()
                )
                .toList();
    }

    public List<TopStatMemberResponse> getTopDefenceMembers() {
        final List<Record> records = recordRepository.findByDefencePointsDESC(PageRequest.of(0, 3));

        return records.stream()
                .map(s -> TopStatMemberResponse
                        .builder()
                        .userName(
                                userRepository
                                        .findById(s.getUserId())
                                        .orElseThrow(RuntimeException::new)
                                        .getName()
                        )
                        .stats(s.getDefence())
                        .build()
                )
                .toList();
    }
    public List<TopStatMemberResponse> getTopAttendanceMembers() {
        final List<Record> records = recordRepository.findByAttendancePointsDESC(PageRequest.of(0, 3));

        return records.stream()
                .map(s -> TopStatMemberResponse
                        .builder()
                        .userName(
                                userRepository
                                        .findById(s.getUserId())
                                        .orElseThrow(RuntimeException::new)
                                        .getName()
                        )
                        .stats(s.getAttendance())
                        .build()
                )
                .toList();
    }
}
