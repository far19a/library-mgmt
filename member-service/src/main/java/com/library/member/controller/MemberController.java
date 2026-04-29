package com.library.member.controller;

import com.library.member.model.Member;
import com.library.member.service.MemberDirectoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/members")
public class MemberController {

    private final MemberDirectoryService memberDirectoryService;

    public MemberController(MemberDirectoryService memberDirectoryService) {
        this.memberDirectoryService = memberDirectoryService;
    }

    @GetMapping
    public List<Member> getAll() {
        return memberDirectoryService.findAll();
    }

    @GetMapping("/active")
    public List<Member> getActiveMembers() {
        return memberDirectoryService.findActive();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Member> getById(@PathVariable Long id) {
        return memberDirectoryService.findById(id)
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Member create(@RequestBody Member member) {
        return memberDirectoryService.save(member);
    }
}
