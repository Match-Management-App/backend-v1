package com.match_management.demo.vote;

import com.match_management.demo.auth.AuthUser;
import com.match_management.demo.board.Board;
import com.match_management.demo.board.BoardRepository;
import com.match_management.demo.match.Match;
import com.match_management.demo.match.MatchRepository;
import com.match_management.demo.match.exception.MatchException;
import com.match_management.demo.member.Member;
import com.match_management.demo.member.MemberRepository;
import com.match_management.demo.member.exception.MemberException;
import com.match_management.demo.member.exception.MemberException.NoMemberException;
import com.match_management.demo.vote.dto.VoteMatchRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.match_management.demo.vote.dto.MonthlyAttendanceResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class VoteService {
    private final VoteRepository voteRepository;
    private final MemberRepository memberRepository;
    private final MatchRepository matchRepository;
    @Transactional
    public Long create(final AuthUser authUser, final VoteMatchRequest voteMatchRequest) {
        final Member member = memberRepository.findByOauthId(authUser.getOauthId())
                .orElseThrow(MemberException.NoMemberException::new);
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        final Match match = matchRepository.findByDate(LocalDateTime.parse(voteMatchRequest.getMatchDay(), formatter))
                .orElseThrow(MatchException.NoMatchException::new);

        final Vote vote = Vote
                .builder()
                .userId(member.getId())
                .matchId(match.getId())
                .isAttendance(voteMatchRequest.isAttendance())
                .date(match.getDate())
                .build();

        voteRepository.save(vote);

        if (voteMatchRequest.isAttendance()) {
            match.amendAttendance();
        }

        return vote.getId();
    }

    public List<Integer> result(final Long matchId) {
        final List<Vote> voteList = voteRepository.findAllByMatchId(matchId);

        if (voteList.size() == 0) {
            return List.of(0, 0);
        }

        final Map<Boolean, Long> attendanceCounts = voteList.stream()
                .collect(Collectors.partitioningBy(Vote::isAttendance, Collectors.counting()));

        return List.of(attendanceCounts.get(true).intValue(), attendanceCounts.get(false).intValue());
    }

    //참석 투표한 사람 이름
    public List<String> attendNameList(final Long matchId) {
        final List<Vote> attendList = voteRepository.findAllByBoardIdAndAttendanceIsTrue(matchId)
                .orElse(null);

        if (attendList == null) {
            return new ArrayList<>();
        }

        return attendList.stream()
                .map(v -> memberRepository.findById(v.getUserId()).orElseThrow(NoMemberException::new))
                .map(Member::getName)
                .toList();
    }

    //불참 투표한 사람 이름
    public List<String> absentNameList(final Long matchId) {
        final List<Vote> absentList = voteRepository.findAllByBoardIdAndAttendanceIsFalse(matchId)
                .orElse(null);

        if (absentList == null) {
            return new ArrayList<>();
        }

        return absentList.stream()
                .map(v -> memberRepository.findById(v.getUserId()).orElseThrow(NoMemberException::new))
                .map(Member::getName)
                .toList();
    }

    //월별 참석 여부
    //TODO 참석자 몇명인지 추가해야함
    public List<MonthlyAttendanceResponse> findAttendanceByMonth(final Long userId, final int year, final int month) {
        final List<Vote> votes = voteRepository.findByUserIdMonthlyAttendance(userId, year, month);

        return votes.stream()
                .map(v -> MonthlyAttendanceResponse
                        .builder()
                        .matchDay(v.getMatchDate())
                        .attendance(v.isAttendance())
                        .build())
                .toList();
    }
}
