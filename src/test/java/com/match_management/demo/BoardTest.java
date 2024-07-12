package com.match_management.demo;

import com.match_management.demo.board.Board;
import com.match_management.demo.board.BoardRepository;
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
public class BoardTest {
    @Autowired
    BoardService boardService;
    @Autowired
    BoardRepository boardRepository;
    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;
    private static Long USER_ID;

    @BeforeEach
    public void initUser() {
        String name = "suhwpark";
        String position = "middleFielder";

        USER_ID = userService.create(name, position);
    }

    //글쓴유저의 id를 Board가 가지고 있는지 확인
    @Test
    public void create() {
        //given
        User user = userRepository.findById(USER_ID).orElseThrow(RuntimeException::new);

        //when
        Long boardId = boardService.create(user.getId(), user.getName(), "내일 경기 투표");
        Board board = boardRepository.findById(boardId).orElseThrow(RuntimeException::new);

        //then
        assertThat(board.getUserId()).isEqualTo(USER_ID);
    }

    //글을 수정할 때 수정이 잘 되는지
    @Test
    public void amendBoard() {
        //given
        User user = userRepository.findById(USER_ID).orElseThrow(RuntimeException::new);

        //given
        Long boardId = boardService.create(user.getId(), user.getName(), "내일 경기 투표");
        boardService.amend(boardId, "앙");
        Board board = boardRepository.findById(boardId).orElseThrow(RuntimeException::new);

        //then
        assertThat(board.getTitle()).isEqualTo("앙");
    }

}
