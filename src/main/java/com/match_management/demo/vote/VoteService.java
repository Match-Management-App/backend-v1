package com.match_management.demo.vote;

import com.match_management.demo.board.Board;
import com.match_management.demo.board.BoardRepository;
import com.match_management.demo.user.User;
import com.match_management.demo.user.UserRepository;
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
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    @Transactional
    public Long create(final Long userId, final Long boardId, final boolean isAttendance) {
        final Board board = boardRepository.findById(boardId).orElseThrow(RuntimeException::new);
        final Vote vote = Vote
                .builder()
                .userId(userId)
                .boardId(boardId)
                .isAttendance(isAttendance)
                .date(board.getMatchDate())
                .build();

        voteRepository.save(vote);

        return vote.getId();
    }

    public List<Integer> result(final Long boardId) {
        final List<Vote> voteList = voteRepository.findAllByBoardId(boardId);

        if (voteList.size() == 0) {
            return List.of(0, 0);
        }

        final Map<Boolean, Long> attendanceCounts = voteList.stream()
                .collect(Collectors.partitioningBy(Vote::isAttendance, Collectors.counting()));

        return List.of(attendanceCounts.get(true).intValue(), attendanceCounts.get(false).intValue());
    }

    //참석 투표한 사람 이름
    public List<String> attendNameList(final Long boardId) {
        final List<Vote> attendList = voteRepository.findAllByBoardIdAndAttendanceIsTrue(boardId)
                .orElse(null);

        if (attendList == null) {
            return new ArrayList<>();
        }

        return attendList.stream()
                .map(v -> userRepository.findById(v.getUserId()).orElseThrow(RuntimeException::new))
                .map(User::getName)
                .toList();
    }

    //불참 투표한 사람 이름
    public List<String> absentNameList(final Long boardId) {
        final List<Vote> absentList = voteRepository.findAllByBoardIdAndAttendanceIsFalse(boardId)
                .orElse(null);

        if (absentList == null) {
            return new ArrayList<>();
        }

        return absentList.stream()
                .map(v -> userRepository.findById(v.getUserId()).orElseThrow(RuntimeException::new))
                .map(User::getName)
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
