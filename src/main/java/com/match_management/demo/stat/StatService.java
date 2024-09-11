package com.match_management.demo.stat;

import com.match_management.demo.auth.AuthUser;
import com.match_management.demo.member.Member;
import com.match_management.demo.member.MemberRepository;
import com.match_management.demo.member.exception.MemberException;
import com.match_management.demo.record.RecordService;
import com.match_management.demo.stat.dto.RecentlyStatResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StatService {
    final private StatRepository statRepository;
    final private RecordService recordService;
    final private MemberRepository memberRepository;

    @Transactional
    public Long create(final Long oauthId, final String matchDate,
                       final int goal, final int assist, final int defence) {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        final Member member = memberRepository.findByOauthId(oauthId)
                .orElseThrow(MemberException.NoMemberException::new);

        final Stat stat = Stat.builder()
                .userId(member.getId())
                .matchDate(LocalDateTime.parse(matchDate, formatter))
                .goal(goal)
                .assist(assist)
                .defence(defence)
                .build();

        statRepository.save(stat);

        if (goal != 0) {
            recordService.accumulateGoalsStat(oauthId, goal);
        }
        if (assist != 0) {
            recordService.accumulateAssistsStat(oauthId, assist);
        }
        if (defence != 0) {
            recordService.accumulateDefencesStat(oauthId, defence);
        }

        return stat.getId();
    }

    //최근 4경기 내 기록
    public List<RecentlyStatResponse> getStats(final AuthUser authUser) {
        List<Stat> stats = statRepository.findTop4DECS(
                authUser.getOauthId(),
                PageRequest.of(0, 4)
        );

        return stats.stream()
                .map(s -> RecentlyStatResponse
                        .builder()
                        .date(s.getMatchDate())
                        .goals(s.getGoal())
                        .assist(s.getAssist())
                        .defence(s.getDefence())
                        .build()
                )
                .toList();
    }
}
