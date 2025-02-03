package com.shop.shop.member.controller;

import com.shop.shop.member.dto.MemberFormDto;
import com.shop.shop.member.entity.Member;
import com.shop.shop.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

//    @PostMapping("/register")
//    public String registerMember(@Valid @RequestBody MemberFormDto memberFormDto) {
//        // 유효성 검사를 통과한 후 회원 가입 처리
//        Member member = memberService.createMember(memberFormDto);
//
//        if (member != null) {
//            return "회원 가입 성공";
//        } else {
//            return "회원 가입 실패";
//        }
//
//    }

    @PostMapping("/register")
    public ResponseEntity<String> createMember(@Valid @RequestBody MemberFormDto memberFormDto, BindingResult result) {

        if (result.hasErrors()) {
            StringBuilder errorMessages = new StringBuilder();
            result.getAllErrors().forEach(error -> {
                errorMessages.append(error.getDefaultMessage()).append("\n");
            });
            return ResponseEntity.badRequest().body(errorMessages.toString());
        }

        try {
            Member createdMember = memberService.createMember(memberFormDto);
            return ResponseEntity.ok().body("회원가입 성공");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }

}
