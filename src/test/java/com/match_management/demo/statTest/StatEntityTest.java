package com.match_management.demo.statTest;

import com.match_management.demo.stat.Stat;
import com.match_management.demo.stat.StatService;
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
public class StatEntityTest {

    @Autowired
    private StatService statService;

    @BeforeEach
    public void setUp() {
        Long userId = 1L;
        Long savedId = statService.initStat(userId);
    }

    //처음 회원 가입 했을때, stat을 init하는 함수
    @Test
    public void initStatEntity() {
        //given
        Long userId = 1L;

        //when
        Long savedId = statService.initStat(userId);
        Stat stat = statService.findOne(userId);

        //then
        assertThat(savedId).isEqualTo(stat.getUserId());
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
        statService.accumulateGoalsStat(userId, goals);
        statService.accumulateAssistsStat(userId, assist);
        statService.accumulateAttendanceStat(userId, attendance);
        statService.accumulateDefencesStat(userId, defence);

        //then
        Stat stat = statService.findOne(userId);
        assertThat((long) goals).isEqualTo(stat.getGoalPoints());
        assertThat((long) assist).isEqualTo(stat.getAssistPoints());
        assertThat((long) attendance).isEqualTo(stat.getAssistPoints());
        assertThat((long) defence).isEqualTo(stat.getDefencePoints());
    }
}
