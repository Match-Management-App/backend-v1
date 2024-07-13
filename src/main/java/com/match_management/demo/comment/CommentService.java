package com.match_management.demo.comment;

import java.util.Comparator;
import java.util.List;
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

    public List<Comment> viewAllComments(final Long boardId) {
        final List<Comment> commentList = commentRepository.findAllByBoardId(boardId)
                .orElseThrow(RuntimeException::new);
        commentList.sort(Comparator.comparing(Comment::getUpdatedAt));
        return commentList;
    }

    @Transactional
    public void amend(final Long commentId, final String amendText) {
        final Comment comment = commentRepository.findById(commentId)
                .orElseThrow(RuntimeException::new);

        comment.amend(amendText);
    }
}
