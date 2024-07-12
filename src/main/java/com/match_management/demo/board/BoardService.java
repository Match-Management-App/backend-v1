package com.match_management.demo.board;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
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

    @Transactional
    public void delete(final Long boardId) {
        final Board board = boardRepository.findById(boardId).orElseThrow(RuntimeException::new);

        boardRepository.delete(board);
    }
}
