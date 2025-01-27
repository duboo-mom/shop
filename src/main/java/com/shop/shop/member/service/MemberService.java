package com.shop.shop.member.service;

import com.shop.shop.member.entity.Member;
import com.shop.shop.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

public Member saveMember(Member member) {
        validationDuplicateMember(member);
        return memberRepository.save(member);
    }

    public void validationDuplicateMember(Member member) {
        Member findMember = memberRepository.findByEmail(member.getEmail());
        if (findMember != null) {
            throw new IllegalStateException("이미 가입된 회원입니다.");
        }

    }
}
