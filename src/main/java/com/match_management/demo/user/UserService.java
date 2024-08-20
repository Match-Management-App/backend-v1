package com.match_management.demo.user;

import com.match_management.demo.record.RecordService;
import com.match_management.demo.user.exception.UserException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;
    private final RecordService recordService;

    @Transactional
    public User create(final Long oauthId, final String name, final String position) {
        final User user = new User(oauthId, name, position);

        userRepository.save(user);
        final Long statId = recordService.initStat(user.getId());
        user.setStatId(statId);

        return user;
    }

    public String getUserNameByAssistId(final Long assistId) {
        return userRepository.findByOauthId(assistId).orElseThrow(UserException.NoUserException::new).getName();
    }

    public String getUserNameByGoalId(final Long goalId) {
        return userRepository.findByOauthId(goalId).orElseThrow(UserException.NoUserException::new).getName();
    }
}
