package com.library.member.service;

import com.library.member.model.Member;
import com.library.member.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MemberDirectoryService {

    private final MemberRepository memberRepository;

    public MemberDirectoryService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    public Optional<Member> findById(Long id) {
        return memberRepository.findById(id);
    }

    public List<Member> findActive() {
        return memberRepository.findByMembershipStatus("ACTIVE");
    }

    public Member save(Member member) {
        return memberRepository.save(member);
    }
}
