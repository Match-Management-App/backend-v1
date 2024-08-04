package com.match_management.demo.comment;

import com.match_management.demo.board.BoardService;
import com.match_management.demo.comment.dto.CommentsResponse;
import com.match_management.demo.user.User;
import com.match_management.demo.user.UserRepository;
import com.match_management.demo.user.UserService;
import java.time.LocalDateTime;
import java.util.List;
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

        USER_ID = userService.create(1L, name, position);
        User user = userRepository.findById(USER_ID).orElseThrow(RuntimeException::new);

        //when
        BOARD_ID = boardService.create(user.getId(), user.getName(),
                "내일 경기 투표", LocalDateTime.of(2024, 7, 22, 9, 0));
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

    //게시글의 모든 댓글 조회 (시간별 오름차순)
    @Test
    public void viewComment() {
        //given
        String text1 = "ㅎㅇ";
        String text2 = "경기함??";
        String text3 = "ㅇㅇ";

        commentService.create(USER_ID, BOARD_ID, text1);
        commentService.create(USER_ID, BOARD_ID, text2);
        commentService.create(USER_ID, BOARD_ID, text3);

        //when
        List<CommentsResponse> commentList = commentService.viewAllComments(BOARD_ID);

        //then
        assertThat(commentList.get(0).getText()).isEqualTo(text1);
        assertThat(commentList.get(1).getText()).isEqualTo(text2);
        assertThat(commentList.get(2).getText()).isEqualTo(text3);
    }

    //댓글 수정
    @Test
    public void amendComment() {
        //given
        String text1 = "ㅎㅇ";
        String amendText = "안녕하세여~";

        //when
        Long commentId = commentService.create(USER_ID, BOARD_ID, text1);
        commentService.amend(commentId, amendText);

        Comment comment = commentRepository.findByBoardId(BOARD_ID)
                .orElseThrow(RuntimeException::new);
        //then
        assertThat(comment.getText()).isEqualTo(amendText);
    }
}
