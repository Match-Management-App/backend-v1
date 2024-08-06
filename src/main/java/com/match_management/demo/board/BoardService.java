package com.match_management.demo.board;

import java.time.LocalDateTime;

import com.match_management.demo.board.dto.BoardIdResponse;
import com.match_management.demo.board.dto.BoardResponse;
import com.match_management.demo.vote.VoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {
    private final BoardRepository boardRepository;
    private final VoteRepository voteRepository;

    @Transactional
    public BoardIdResponse create(final Long userId, final String writer,
                                  final String title, final LocalDateTime matchDate) {
        final Board board = Board.builder()
                .userId(userId)
                .writer(writer)
                .title(title)
                .matchDate(matchDate)
                .build();
        boardRepository.save(board);

        return BoardIdResponse.builder().boardId(board.getId()).build();
    }

    @Transactional
    public BoardResponse amendTitle(final Long boardId, final String title) {
        final Board board = boardRepository.findById(boardId).orElseThrow(RuntimeException::new);

        board.amendTitle(title);
        return BoardResponse
                .builder()
                .boardId(board.getId())
                .title(board.getTitle())
                .writer(board.getWriter())
                .matchDate(board.getMatchDate())
                .build();
    }

    @Transactional
    public BoardResponse amendMatchDate(final Long boardId, final LocalDateTime matchDate) {
        final Board board = boardRepository.findById(boardId).orElseThrow(RuntimeException::new);

        board.amendMatchDate(matchDate);

        return BoardResponse
                .builder()
                .boardId(board.getId())
                .title(board.getTitle())
                .writer(board.getWriter())
                .matchDate(board.getMatchDate())
                .build();
    }

    @Transactional
    public BoardIdResponse delete(final Long boardId) {
        final Board board = boardRepository.findById(boardId).orElseThrow(RuntimeException::new);

        boardRepository.delete(board);
        return BoardIdResponse.builder().boardId(boardId).build();
    }

    public Page<BoardResponse> viewAllBoard(final Pageable pageable) {
        int page = pageable.getPageNumber() - 1;
        int pageLimit = 10;

        final Page<Board> boards = boardRepository
                .findAll(PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "createAt")));

        return boards.map(b -> BoardResponse
                .builder()
                .boardId(b.getId())
                .title(b.getTitle())
                .writer(b.getWriter())
                .matchDate(b.getMatchDate())
                .participant(
                        voteRepository.findAllByBoardIdAndAttendanceIsTrue(b.getId())
                                .orElseThrow(RuntimeException::new).size()
                )
                .absentee(
                        voteRepository.findAllByBoardIdAndAttendanceIsFalse(b.getId())
                                .orElseThrow(RuntimeException::new).size()
                )
                .build());
    }
}
