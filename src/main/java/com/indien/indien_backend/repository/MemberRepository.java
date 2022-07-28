package com.indien.indien_backend.repository;

import java.util.Optional;

import com.indien.indien_backend.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MemberRepository extends JpaRepository<Member, Long>
{
    Optional<Member> findMemberById(Long id);
    Optional<Member> findByEmail(String email);
    boolean existsMemberByEmail(String email);

    Optional<Member> findByEmailAndProvider(String email, String provider);
}
