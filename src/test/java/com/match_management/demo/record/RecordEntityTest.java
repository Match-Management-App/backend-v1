package com.match_management.demo.record;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
@SpringBootTest
@Transactional
@Rollback
public class RecordEntityTest {

    @Autowired
    private RecordService recordService;
    @Autowired
    private RecordRepository recordRepository;

    @BeforeEach
    public void setUp() {
        Long userId = 1L;
        Long savedId = recordService.initStat(userId);
    }

    //처음 회원 가입 했을때, stat을 init하는 함수
    @Test
    public void initStatEntity() {
        //given
        Long userId = 1L;

        //when
        Long savedId = recordService.initStat(userId);
        Record record = recordRepository.findByUserId(userId)
                .orElseThrow(RuntimeException::new);

        //then
        assertThat(savedId).isEqualTo(record.getUserId());
    }

    //경기 끝난 후 stat을 적용하는 서비스
    @Test
    public void setStats() {
        //give
        // 곧 jwt 정보로 바뀔 예정
        Long userId = 1L;
        int goals = 2;
        int assist = 1;
        int attendance = 1;
        int defence = 1;

        //when
        recordService.accumulateGoalsStat(userId, goals);
        recordService.accumulateAssistsStat(userId, assist);
        recordService.accumulateAttendanceStat(userId, attendance);
        recordService.accumulateDefencesStat(userId, defence);

        //then
        Record record = recordRepository.findByUserId(userId)
                .orElseThrow(RuntimeException::new);
        assertThat(goals).isEqualTo(record.getGoal());
        assertThat(assist).isEqualTo(record.getAssist());
        assertThat(attendance).isEqualTo(record.getAttendance());
        assertThat(defence).isEqualTo(record.getDefence());
    }
}
