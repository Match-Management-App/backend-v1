package com.match_management.demo.match;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@Transactional
@Rollback
public class MatchTest {
    @Autowired
    MatchService matchService;

    @Test
    public void create() throws Exception {
        //given
        Long id = matchService.create(2024, 7, 20, 9, "탄천 유수지");

        //when
        Match match = matchRepository.findById(id);

        //then
        assertThat(match.getDate()).isEqualTo(LocalDateTime.of(2024, 7, 20, 9));
    }
}
