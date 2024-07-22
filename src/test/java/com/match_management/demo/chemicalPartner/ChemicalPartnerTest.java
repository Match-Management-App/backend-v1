package com.match_management.demo.chemicalPartner;

import com.match_management.demo.user.UserService;
import java.time.LocalDate;
import java.time.Month;
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
public class ChemicalPartnerTest {

    @Autowired
    UserService userService;

    @Autowired
    ChemicalPartnerService chemicalPartnerService;
    private static Long user1;
    private static Long user2;
    private static Long user3;
    private static Long user4;
    private static Long user5;
    private static Long user6;
    private final static LocalDate matchDate = LocalDate.of(2024, Month.JUNE, 13);

    @BeforeEach
    public void setUp() {
        user1 = userService.create("수환", "middleFielder");
        user2 = userService.create("용수", "forward");
        user3 = userService.create("상화", "middleFielder");
        user4 = userService.create("용범", "forward");
        user5 = userService.create("재국", "defender");
        user6 = userService.create("우규", "middleFielder");
    }

    // 골 넣은 사람과 어시스트한 사람과 골 갯수를 통해, chemical table 생성
    @Test
    public void viewMyBestAssistsChemicalPartner() {
        //given
        chemicalPartnerService.create(user1, user3, 3, matchDate);
        chemicalPartnerService.create(user1, user2, 2, matchDate);
        chemicalPartnerService.create(user1, user5, 4, matchDate);
        chemicalPartnerService.create(user1, user6, 1, matchDate);

        //when
        List<String> names = chemicalPartnerService.getAssistPartner(user1);

        //then
        assertThat(names.size()).isEqualTo(3);
        assertThat(names.get(0)).isEqualTo("재국");
        assertThat(names.get(1)).isEqualTo("상화");
        assertThat(names.get(2)).isEqualTo("용수");
    }

    @Test
    public void viewMyBestGoalChemicalPartner() {
        //given
        chemicalPartnerService.create(user3, user1, 3, matchDate);
        chemicalPartnerService.create(user5, user1, 2, matchDate);
        chemicalPartnerService.create(user6, user1, 4, matchDate);
        chemicalPartnerService.create(user2, user1, 1, matchDate);

        //when
        List<String> names = chemicalPartnerService.getGoalPartner(user1);

        //then
        assertThat(names.size()).isEqualTo(3);
        assertThat(names.get(0)).isEqualTo("우규");
        assertThat(names.get(1)).isEqualTo("상화");
        assertThat(names.get(2)).isEqualTo("재국");
    }
}
