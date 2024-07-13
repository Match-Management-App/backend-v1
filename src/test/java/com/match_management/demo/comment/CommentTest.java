package com.match_management.demo.comment;

import com.match_management.demo.board.Board;
import com.match_management.demo.board.BoardRepository;
import com.match_management.demo.board.BoardService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@SpringBootTest
@Transactional
@Rollback
public class CommentTest {

    @Autowired
    BoardService boardService;
    @Autowired
    CommentService commentService;
    @Autowired
    CommentRepository commentRepository;

    //게시글에 댓글 달기
    @Test
    public void leaveAComment() {
        //given
        String text = "용병 1";

        //when
        Long commentId = commentService.create(userId, boardId, text);

        //then
        Comment comment = commentRepository.findById(commentId);
        assertThat(commnet.getText()).isEqualTo(text);
    }
}
