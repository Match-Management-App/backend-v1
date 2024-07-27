package com.match_management.demo.match;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
