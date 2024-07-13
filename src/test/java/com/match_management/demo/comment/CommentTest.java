package com.match_management.demo.comment;

import com.match_management.demo.board.BoardService;
import com.match_management.demo.user.User;
import com.match_management.demo.user.UserRepository;
import com.match_management.demo.user.UserService;
import org.junit.jupiter.api.BeforeEach;
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
    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;
    private static Long USER_ID;
    private static Long BOARD_ID;
    @BeforeEach
    public void initUserAndBoard() {
        String name = "suhwpark";
        String position = "middleFielder";

        USER_ID = userService.create(name, position);
        User user = userRepository.findById(USER_ID).orElseThrow(RuntimeException::new);

        //when
        BOARD_ID = boardService.create(user.getId(), user.getName(), "내일 경기 투표");
    }

    //게시글에 댓글 달기
    @Test
    public void leaveAComment() {
        //given
        String text = "용병 1";

        //when
        Long commentId = commentService.create(USER_ID, BOARD_ID, text);

        //then
        Comment comment = commentRepository.findByBoardId(commentId)
                .orElseThrow(RuntimeException::new);
        assertThat(comment.getText()).isEqualTo(text);
    }
}
