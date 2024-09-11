package com.match_management.demo.match;

import com.match_management.demo.match.dto.MonthlyScheduleResponse;
import com.match_management.demo.match.dto.NextMatchResponse;
import com.match_management.demo.match.dto.SetNextMatchRequest;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MatchService {
    private final MatchRepository matchRepository;

    @Transactional
    public Long create(final int year, final int month,
                       final int day, final int hour, final String spot) {
        final Match match = Match
                .builder()
                .year(year)
                .month(month)
                .day(day)
                .hour(hour)
                .spot(spot)
                .build();

        matchRepository.save(match);
        return match.getId();
    }

    // 다음 일정
    public NextMatchResponse findNextMatch() {
        final Match match = matchRepository.findNextMatch(LocalDateTime.now())
                .orElse(null);

        if (match == null) {
            return NextMatchResponse.builder().build();
        }

        return NextMatchResponse
                .builder()
                .matchDay(match.getDate())
                .participants(match.getAttendance())
                .spot(match.getSpot())
                .build();
    }

    // 월별 일정 4개
    public List<MonthlyScheduleResponse> findSchedulesByMonth(final int year, final int month) {
        final List<Match> matches = matchRepository.findByMonth(year, month);

        return matches.stream()
                .map(m -> MonthlyScheduleResponse
                        .builder()
                        .matchDay(m.getDate())
                        .spot(m.getSpot())
                        .build())
                .toList();
    }

    @Transactional
    public void setMatch(final SetNextMatchRequest setNextMatchRequest) {
        final List<Integer> split = Arrays.stream(setNextMatchRequest.getNextMatch().split("-"))
                .map(Integer::parseInt)
                .toList();
        create(split.get(0), split.get(1), split.get(2), setNextMatchRequest.getHour(), setNextMatchRequest.getSpot());
    }
}
