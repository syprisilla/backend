package com.board.Controller;


import com.board.Entity.MemberEntity;
import com.board.repository.MemberRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/members")
public class MemberController {
    private final MemberRepository repository;

    public MemberController(MemberRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public MemberEntity createMember(@RequestBody MemberEntity member) {
        return repository.save(member);
    }
    @GetMapping
    public List<MemberEntity> findAll() {
        return repository.findAll();
    }
}