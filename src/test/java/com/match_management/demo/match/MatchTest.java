package com.match_management.demo.match;

import java.time.LocalDateTime;
import java.util.List;

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
    @Autowired
    MatchRepository matchRepository;

    @Test
    public void create() {
        //given
        Long id = matchService.create(2024, 7, 20, 9, "탄천 유수지");

        //when
        Match match = matchRepository.findById(id)
                .orElseThrow(RuntimeException::new);

        //then
        assertThat(match.getDate()).isEqualTo(LocalDateTime.of(2024, 7, 20, 9, 0));
        assertThat(match.getSpot()).isEqualTo("탄천 유수지");
    }

    @Test
    public void findByMonth() {
        //given
        matchService.create(2024, 7, 27, 9, "탄천 유수지");
        matchService.create(2024, 7, 20, 9, "탄천 유수지");
        matchService.create(2024, 7, 13, 9, "탄천 유수지");
        matchService.create(2024, 7, 6, 9, "탄천 유수지");
        matchService.create(2024, 6, 10, 9, "탄천 유수지");
        matchService.create(2024, 6, 3, 9, "탄천 유수지");
        matchService.create(2024, 6, 17, 9, "탄천 유수지");
        matchService.create(2024, 5, 22, 9, "탄천 유수지");
        matchService.create(2024, 5, 15, 9, "탄천 유수지");
        matchService.create(2024, 3, 20, 9, "탄천 유수지");

        //when
        List<Match> julyMatch = matchRepository.findByMonth(2024, 7);
        List<Match> juneMatch = matchRepository.findByMonth(2024, 6);
        List<Match> mayMatch = matchRepository.findByMonth(2024, 5);
        List<Match> aprilMatch = matchRepository.findByMonth(2024, 3);

        //then
        assertThat(julyMatch.size()).isEqualTo(4);
        assertThat(juneMatch.size()).isEqualTo(3);
        assertThat(mayMatch.size()).isEqualTo(2);
        assertThat(aprilMatch.size()).isEqualTo(1);
    }

    //투표가 끝나고 참석 인원 set 해주는 함수
    @Test
    public void setAttendance() {
        //given
        Long id = matchService.create(2024, 7, 27, 9, "탄천 유수지");
        //when
        Match match = matchRepository.findById(id).orElseThrow(RuntimeException::new);
        match.setAttendance(20);
        //then
        assertThat(match.getAttendance()).isEqualTo(20);
    }
}
