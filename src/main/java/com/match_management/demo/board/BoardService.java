package com.match_management.demo.board;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    @Transactional
    public Long create(final Long userId, final String writer, final String title) {
        final Board board = new Board(userId, writer, title);
        boardRepository.save(board);

        return board.getId();
    }

    @Transactional
    public void amend(final Long boardId, final String title) {
        final Board board = boardRepository.findById(boardId).orElseThrow(RuntimeException::new);

        board.amendTitle(title);
    }
}
