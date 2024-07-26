package com.match_management.demo.vote;

import com.match_management.demo.board.BoardRepository;
import com.match_management.demo.board.BoardService;
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

        USER_ID = userService.create(1L, name, position);
        User user = userRepository.findById(USER_ID).orElseThrow(RuntimeException::new);

        //when
        BOARD_ID = boardService.create(user.getId(), user.getName(),
                "내일 경기 투표", LocalDateTime.of(2024, 7, 22, 9, 0));
    }

    // 한명이 참석으로 투표했을 경우의 test
    @Test
    public void viewVoteResult() {
        //given
        voteService.create(USER_ID, BOARD_ID, true);

        //when
        //dto로 변경 예정
        List<Integer> voteResult = voteService.result(BOARD_ID);

        //then
        assertThat(voteResult.get(0)).isEqualTo(1);
    }

    //한명도 투표하지 않았을 경우
    @Test
    public void viewResultNoOneVote() {
        //when
        List<Integer> voteResult = voteService.result(BOARD_ID);

        //then
        assertThat(voteResult.get(0)).isEqualTo(0);
        assertThat(voteResult.get(1)).isEqualTo(0);
    }

    //투표를 참석으로 한 사람의 명단
    @Test
    public void attendName() {
        //given
        Long user1 = userService.create(1L, "su", "forwoard");
        Long user2 = userService.create(2L, "suhwpark", "forwoard");
        Long user3 = userService.create(3L, "say", "forwoard");
        Long user4 = userService.create(4L, "niu", "forwoard");
        Long user5 = userService.create(5L, "yong", "forwoard");

        voteService.create(USER_ID, BOARD_ID, true);
        voteService.create(user1, BOARD_ID, true);
        voteService.create(user2, BOARD_ID, true);
        voteService.create(user3, BOARD_ID, false);
        voteService.create(user4, BOARD_ID, true);
        voteService.create(user5, BOARD_ID, false);

        //when
        List<String> result = voteService.attendNameList(BOARD_ID);

        //then
        assertThat(result.size()).isEqualTo(4);
        assertThat(result.contains("su")).isTrue();
        assertThat(result.contains("suhwpark")).isTrue();
        assertThat(result.contains("niu")).isTrue();
        assertThat(result.contains("say")).isFalse();
        assertThat(result.contains("yong")).isFalse();
    }

    //투표를 불참으로 한 사람의 명단
    @Test
    public void absentName() {
        //given
        Long user1 = userService.create(1L, "su", "forwoard");
        Long user2 = userService.create(2L, "suhwpark", "forwoard");
        Long user3 = userService.create(3L, "say", "forwoard");
        Long user4 = userService.create(4L, "niu", "forwoard");
        Long user5 = userService.create(5L, "yong", "forwoard");

        voteService.create(USER_ID, BOARD_ID, true);
        voteService.create(user1, BOARD_ID, true);
        voteService.create(user2, BOARD_ID, true);
        voteService.create(user3, BOARD_ID, false);
        voteService.create(user4, BOARD_ID, true);
        voteService.create(user5, BOARD_ID, false);

        //when
        List<String> result = voteService.absentNameList(BOARD_ID);

        //then
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.contains("su")).isFalse();
        assertThat(result.contains("suhwpark")).isFalse();
        assertThat(result.contains("niu")).isFalse();
        assertThat(result.contains("say")).isTrue();
        assertThat(result.contains("yong")).isTrue();
    }
}
