package com.match_management.demo.user;

import com.match_management.demo.auth.AuthUser;
import com.match_management.demo.record.RecordService;
import com.match_management.demo.user.exception.MemberException.NoMemberException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;
    private final RecordService recordService;

    @Transactional
    public Member create(final Long oauthId, final String name, final String position) {
        final Member member = new Member(oauthId, name, position);

        memberRepository.save(member);
        final Long statId = recordService.initStat(member.getId());
        member.setStatId(statId);

        return member;
    }

    public String getMemberNameByAssistId(final Long assistId) {
        return memberRepository.findByOauthId(assistId).orElseThrow(NoMemberException::new).getName();
    }

    public String getMemberNameByGoalId(final Long goalId) {
        return memberRepository.findByOauthId(goalId).orElseThrow(NoMemberException::new).getName();
    }

    @Transactional
    public void delete(final AuthUser authUser) {
        memberRepository.delete(
                memberRepository.findByOauthId(authUser.getOauthId())
                        .orElseThrow(NoMemberException::new)
        );
    }
}
