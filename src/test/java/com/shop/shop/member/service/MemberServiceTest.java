package com.shop.shop.member.service;

import com.shop.shop.member.constant.Role;
import com.shop.shop.member.dto.MemberFormDto;
import com.shop.shop.member.entity.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    PasswordEncoder passwordEncoder;

    public MemberFormDto createMemberFormDto() {
        MemberFormDto memberFormDto = MemberFormDto.builder()
                .name("test")
                .email("test@email.com")
                .password("password123")
                .address("서울시 중구")
                .build();

        return memberFormDto;
    }

    @Test
    @DisplayName("회원가입 테스트")
    public void saveMemberTest() {

        MemberFormDto memberFormDto = createMemberFormDto();
        Member savedMember = memberService.createMember(memberFormDto);

        Role savedRole = Role.USER;

        assertNotNull(savedMember);
        assertEquals(memberFormDto.getName(), savedMember.getName());
        assertEquals(memberFormDto.getEmail(), savedMember.getEmail());
        assertEquals(memberFormDto.getPassword(), savedMember.getPassword());
        assertEquals(savedRole, savedMember.getRole());

    }

    @Test
    @DisplayName("중복 회원 가입 테스트")
    public void saveDuplicateMemberTest() {
        MemberFormDto memberFormDto1 = createMemberFormDto();
        MemberFormDto memberFormDto2 = createMemberFormDto();

        memberService.createMember(memberFormDto1);

        Throwable e = assertThrows(IllegalStateException.class, () -> {
            memberService.createMember(memberFormDto2);});

        assertEquals("사용중인 이메일 입니다.", e.getMessage());

    }



}