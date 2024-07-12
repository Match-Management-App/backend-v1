package com.match_management.demo.user;

import com.match_management.demo.stat.Stat;
import com.match_management.demo.stat.StatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final StatService statService;

    @Transactional
    public Long create(final String name, final String position) {
        final User user = new User(name, position);

        userRepository.save(user);
        final Long statId = statService.initStat(user.getId());
        user.setStatId(statId);

        return user.getId();
    }
}
