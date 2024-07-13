package com.match_management.demo.comment;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    Optional<Comment> findByBoardId(final Long boardId);
    Optional<Comment> findById(final Long id);
    Optional<List<Comment>> findAllByBoardId(final Long boardId);
}
