package com.match_management.demo.user;

import com.match_management.demo.record.Record;
import com.match_management.demo.record.RecordRepository;
import com.match_management.demo.record.RecordService;
import com.match_management.demo.stat.Stat;
import com.match_management.demo.stat.StatRepository;
import com.match_management.demo.stat.StatService;
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
public class UserEntityTest {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
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
        User user = userService.create(1L, name, position);

        assertThat(user.getId()).isEqualTo(user.getId());
        assertThat(user.getName()).isEqualTo(name);
        assertThat(user.getPosition()).isEqualTo(position);
    }

    // 유저 생성 했을 때, 초기 stat이 잘 init 되는지
    @Test
    public void ValidStatEntityWhenUserCreate() {
        //given
        String name = "suhwpark";
        String position = "middleFielder";

        //when
        User user = userService.create(1L, name, position);

        //then
        Record record = recordRepository.findByUserId(user.getId()).orElseThrow(RuntimeException::new);

        assertThat(record.getUserId()).isEqualTo(user.getId());
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
        User user = userService.create(1L, name, position);
        Record record = recordRepository.findByUserId(user.getId()).orElseThrow(RuntimeException::new);

        recordService.accumulateGoalsStat(user.getId(), 1);
        recordService.accumulateAssistsStat(user.getId(), 1);
        recordService.accumulateAttendanceStat(user.getId(), 1);
        recordService.accumulateDefencesStat(user.getId(), 1);

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
        User user = userService.create(1L, name, position);;

        //then
        assertThatThrownBy(() -> user.authenticateCustomCode(code))
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
        User user = userService.create(1L, name, position);
        user.authenticateCustomCode(code);
        //then
        assertThat(user.isAuthenticated()).isTrue();
    }
}
