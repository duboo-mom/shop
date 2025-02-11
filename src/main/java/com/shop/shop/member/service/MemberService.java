package com.shop.shop.member.service;

import com.shop.shop.member.constant.Role;
import com.shop.shop.member.dto.MemberFormDto;
import com.shop.shop.member.entity.Member;
import com.shop.shop.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Member member = memberRepository.findByEmail(email).orElseThrow(()
                -> new UsernameNotFoundException("회원을 찾을 수 없습니다."));

        return User.builder()
                .username(member.getName())
                .password(member.getPassword())
                .roles(member.getRole().toString())
                .build();
    }

    @Transactional
    public String createMember(MemberFormDto memberFormDto) {

        Optional<Member> findMember = memberRepository.findByEmail(memberFormDto.getEmail());

        if (findMember.isPresent()) {
            throw new IllegalStateException("사용중인 이메일 입니다.");
        }

        String password = passwordEncoder.encode(memberFormDto.getPassword());

        Member member = Member.builder()
                .name(memberFormDto.getName())
                .email(memberFormDto.getEmail())
                .password(password)
                .address(memberFormDto.getAddress())
                .role(Role.USER)
                .build();

        memberRepository.save(member);

        return "success";
    }

}
