package com.match_management.demo.user;

import com.match_management.demo.record.Record;
import com.match_management.demo.record.RecordRepository;
import com.match_management.demo.record.RecordService;
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
public class MemberEntityTest {
    @Autowired
    private MemberService memberService;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private RecordService recordService;
    @Autowired
    private RecordRepository recordRepository;

    //user을 만들 때, stat entity 같이 생성
    @Test
    public void createUser() {
        //given
        String name = "suhwpark";
        String position = "middleFielder";


        //then
        Member member = memberService.create(1L, name, position);

        assertThat(member.getId()).isEqualTo(member.getId());
        assertThat(member.getName()).isEqualTo(name);
        assertThat(member.getPosition()).isEqualTo(position);
    }

    // 유저 생성 했을 때, 초기 stat이 잘 init 되는지
    @Test
    public void ValidStatEntityWhenUserCreate() {
        //given
        String name = "suhwpark";
        String position = "middleFielder";

        //when
        Member member = memberService.create(1L, name, position);

        //then
        Record record = recordRepository.findByUserId(member.getId()).orElseThrow(RuntimeException::new);

        assertThat(record.getUserId()).isEqualTo(member.getId());
        assertThat(record.getGoal()).isEqualTo(0);
        assertThat(record.getAssist()).isEqualTo(0);
        assertThat(record.getAttendance()).isEqualTo(0);
        assertThat(record.getDefence()).isEqualTo(0);
    }

    //user가 자신의 stat을 올릴때, stat 엔티티에 적용이 되는지 확인
    @Test
    public void accumulateMyOwnStat() throws Exception {
        //given
        String name = "suhwpark";
        String position = "middleFielder";

        //when
        Member member = memberService.create(1L, name, position);
        Record record = recordRepository.findByUserId(member.getId()).orElseThrow(RuntimeException::new);

        recordService.accumulateGoalsStat(member.getId(), 1);
        recordService.accumulateAssistsStat(member.getId(), 1);
        recordService.accumulateAttendanceStat(member.getId(), 1);
        recordService.accumulateDefencesStat(member.getId(), 1);

        //then
        assertThat(record.getGoal()).isEqualTo(1);
        assertThat(record.getAssist()).isEqualTo(1);
        assertThat(record.getAttendance()).isEqualTo(1);
        assertThat(record.getDefence()).isEqualTo(1);
    }

    //지금은 몬유fc에 가입된 사람들만 사용하는 앱이기에, 개인 코드로 authentication 실패할 경우.
    @Test
    public void authenticationWithCustomCodeFailed() throws Exception {
        //given
        String code = "최강몬유FC";

        String name = "suhwpark";
        String position = "middleFielder";

        //when
        Member member = memberService.create(1L, name, position);;

        //then
        assertThatThrownBy(() -> member.authenticateCustomCode(code))
                .isInstanceOf(RuntimeException.class);
    }

    //개인 코드로 authentication가 일치 할 경우.
    @Test
    public void authenticationWithCustomCodeSuccess() throws Exception {
        //given
        String code = "최강강몬유FC";

        String name = "suhwpark";
        String position = "middleFielder";

        //when
        Member member = memberService.create(1L, name, position);
        member.authenticateCustomCode(code);
        //then
        assertThat(member.isAuthenticated()).isTrue();
    }
}
