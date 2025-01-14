package com.shop.shop.member.controller;

import com.shop.shop.member.dto.MemberFormDto;
import com.shop.shop.member.entity.Member;
import com.shop.shop.member.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class MemberController {

    private MemberService memberService;

    @PostMapping("/member/new")
    public String memberForm(@Validated MemberFormDto memberFormDto, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "fail";
        }

        try {
            Member member = Member.createMember(memberFormDto);
            memberService.saveMember(member);
            return "success";
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "중복 회원";
        }

    }

}
