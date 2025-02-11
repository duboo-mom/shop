package com.shop.shop.member.controller;

import com.shop.shop.member.dto.MemberFormDto;
import com.shop.shop.member.entity.Member;
import com.shop.shop.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/new")
    public ResponseEntity<String> createMember(@Valid @RequestBody MemberFormDto memberFormDto) {
        String result = memberService.createMember(memberFormDto);
        return ResponseEntity.ok().body(result);
    }


}
