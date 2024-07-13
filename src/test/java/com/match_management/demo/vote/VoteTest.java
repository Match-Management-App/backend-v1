package com.match_management.demo.vote;

import com.match_management.demo.board.BoardService;
import com.match_management.demo.user.User;
import com.match_management.demo.user.UserRepository;
import com.match_management.demo.user.UserService;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@Transactional
@Rollback
public class VoteTest {
    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    BoardService boardService;
    @Autowired
    VoteService voteService;
    @Autowired
    VoteRepository voteRepository;

    private static Long USER_ID;
    private static Long BOARD_ID;

    @BeforeEach
    public void initBoard() {
        String name = "suhwpark";
        String position = "middleFielder";

        USER_ID = userService.create(name, position);
        User user = userRepository.findById(USER_ID).orElseThrow(RuntimeException::new);

        //when
        BOARD_ID = boardService.create(user.getId(), user.getName(), "내일 경기 투표", true);
    }

    // 한명이 참석으로 투표했을 경우의 test
    @Test
    public void create() {
        //given
        voteService.create(USER_ID, BOARD_ID, true);

        //when
        //dto로 변경 예정
        List<Integer> voteResult = voteService.result(BOARD_ID);

        //then
        assertThat(voteResult.get(0)).isEqualTo(1);
    }
}
