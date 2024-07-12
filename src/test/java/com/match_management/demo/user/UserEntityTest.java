package com.match_management.demo.user;

import com.match_management.demo.stat.Stat;
import com.match_management.demo.stat.StatRepository;
import com.match_management.demo.stat.StatService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@Transactional
@Rollback
public class UserEntityTest {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private StatRepository statRepository;
    @Autowired
    private StatService statService;

    //user을 만들 때, stat entity 같이 생성
    @Test
    public void createUser() {
        //given
        String name = "suhwpark";
        String position = "middleFielder";

        //when
        Long userId = userService.create(name, position);

        //then
        User user = userRepository.findById(userId).orElseThrow(RuntimeException::new);

        assertThat(user.getId()).isEqualTo(userId);
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
        Long userId = userService.create(name, position);

        //then
        User user = userRepository.findById(userId).orElseThrow(RuntimeException::new);
        Stat stat = statRepository.findByUserId(user.getId()).orElseThrow(RuntimeException::new);

        assertThat(stat.getUserId()).isEqualTo(userId);
        assertThat(stat.getGoalPoints()).isEqualTo(0);
        assertThat(stat.getAssistPoints()).isEqualTo(0);
        assertThat(stat.getAttendancePoints()).isEqualTo(0);
        assertThat(stat.getDefencePoints()).isEqualTo(0);
    }

    //user가 자신의 stat을 올릴때, stat 엔티티에 적용이 되는지 확인
    @Test
    public void accumulateMyOwnStat() throws Exception {
        //given
        String name = "suhwpark";
        String position = "middleFielder";

        //when
        Long userId = userService.create(name, position);
        Stat stat = statRepository.findByUserId(userId).orElseThrow(RuntimeException::new);

        statService.accumulateGoalsStat(userId, 1);
        statService.accumulateAssistsStat(userId, 1);
        statService.accumulateAttendanceStat(userId, 1);
        statService.accumulateDefencesStat(userId, 1);

        //then
        assertThat(stat.getGoalPoints()).isEqualTo(1);
        assertThat(stat.getAssistPoints()).isEqualTo(1);
        assertThat(stat.getAttendancePoints()).isEqualTo(1);
        assertThat(stat.getDefencePoints()).isEqualTo(1);
    }
}
