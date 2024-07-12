package com.match_management.demo.user;

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

    @Test
    public void ValidStatEntityWhenUserCreate() {
        //given

        //when
        //then
    }
}
