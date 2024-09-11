package com.match_management.demo.board;

import com.match_management.demo.board.dto.BoardIdResponse;
import com.match_management.demo.member.Member;
import com.match_management.demo.member.MemberRepository;
import com.match_management.demo.member.MemberService;
import java.time.LocalDateTime;
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
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;
    private static Member member;

    @BeforeEach
    public void initUser() {
        String name = "suhwpark";
        String position = "middleFielder";

        member = memberService.create(1L, name, position);
    }

    //글쓴유저의 id를 Board가 가지고 있는지 확인
    //match date가 잘 설정 되어있는지
    @Test
    public void create() {
        //when
        BoardIdResponse response = boardService.create(member.getId(), member.getName(),
                "내일 경기 투표", LocalDateTime.of(2024, 7, 22, 9, 0));
        Board board = boardRepository.findById(response.getBoardId()).orElseThrow(RuntimeException::new);

        //then
        assertThat(board.getUserId()).isEqualTo(BoardTest.member);
        assertThat(board.getMatchDate())
                .isEqualTo(LocalDateTime.of(2024, 7, 22, 9, 0));
    }

    //글을 수정할 때 수정이 잘 되는지
    @Test
    public void amendBoard() {
        //given
        BoardIdResponse response = boardService.create(member.getId(), member.getName(),
                "내일 경기 투표", LocalDateTime.of(2024, 7, 22, 9, 0));
        boardService.amendTitle(response.getBoardId(), "앙");
        boardService.amendMatchDate(response.getBoardId(),
                LocalDateTime.of(2024, 7, 29, 10, 0));
        Board board = boardRepository.findById(response.getBoardId()).orElseThrow(RuntimeException::new);

        //then
        assertThat(board.getTitle()).isEqualTo("앙");
        assertThat(board.getMatchDate()).isEqualTo(LocalDateTime.of(2024, 7, 29, 10, 0));
    }

    //글을 삭제 할 떄
    @Test
    public void delete() {
        //given
        BoardIdResponse response = boardService.create(member.getId(), member.getName(),
                "내일 경기 투표", LocalDateTime.of(2024, 7, 22, 9, 0));
        boardService.delete(response.getBoardId());
        //then

        assertThatThrownBy(() -> boardRepository.findById(response.getBoardId()).orElseThrow(RuntimeException::new))
                .isInstanceOf(RuntimeException.class);
    }
}
