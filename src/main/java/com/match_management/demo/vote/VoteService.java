package com.match_management.demo.vote;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class VoteService {
    private final VoteRepository voteRepository;

    @Transactional
    public Long create(final Long userId, final Long boardId, final boolean isAttendance) {
        final Vote vote = new Vote(userId, boardId, isAttendance);

        voteRepository.save(vote);

        return vote.getId();
    }

    public List<Integer> result(final Long boardId) {
        final List<Vote> voteList = voteRepository.findAllByBoardId(boardId)
                .orElse(null);

        if (voteList == null) {
            return List.of(0, 0);
        }

        final Map<Boolean, Long> attendanceCounts = voteList.stream()
                .collect(Collectors.partitioningBy(Vote::isAttendance, Collectors.counting()));

        return List.of(attendanceCounts.get(true).intValue(), attendanceCounts.get(false).intValue());
    }
}
