package com.match_management.demo.member;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findById(final Long id);
    Optional<Member> findByOauthId(final Long oauthId);
}
