package com.library.member.repository;

import com.library.member.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {
    List<Member> findByMembershipStatus(String membershipStatus);
}
