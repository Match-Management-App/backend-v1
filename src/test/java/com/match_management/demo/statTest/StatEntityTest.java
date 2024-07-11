package com.match_management.demo.statTest;

import com.match_management.demo.stat.Stat;
import com.match_management.demo.stat.StatService;
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

}
