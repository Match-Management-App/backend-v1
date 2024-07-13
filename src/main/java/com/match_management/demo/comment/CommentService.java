package com.match_management.demo.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    @Transactional
    public Long create(final Long userId, final Long boardId, final String text) {
        final Comment comment = new Comment(userId, boardId, text);

        commentRepository.save(comment);
        return comment.getId();
    }
}
