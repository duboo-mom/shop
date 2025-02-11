package com.shop.shop.member.service;


import static org.mockito.Mockito.*;

import com.shop.shop.member.constant.Role;
import com.shop.shop.member.dto.MemberFormDto;
import com.shop.shop.member.entity.Member;
import com.shop.shop.member.repository.MemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class MemberServiceMockTest {

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private MemberService memberService;

//    @BeforeEach
//    void setUp() {
//    }

    @Test
    @DisplayName("회원 가입 성공 테스트")
    void testCreateMember_Success() {
        // Given: 새로운 회원
        MemberFormDto memberFormDto = MemberFormDto.builder()
                .name("test1")
                .email("test1@email.com")
                .password("password123")
                .address("서울시 동대문구")
                .build();

        Mockito.when(memberRepository.findByEmail(memberFormDto.getEmail()))
                .thenReturn(Optional.empty()); // 이메일 중복이 없다고 가정

        Member savedMember = Member.builder()
                .id(1L) // mockito test에서는 수동 입력
                .name("test1")
                .email("test1@email.com")
                .password("password123")
                .address("서울시 동대문구")
                .role(Role.USER)
                .build();

        Mockito.when(memberRepository.save(Mockito.any(Member.class)))
                .thenReturn(savedMember); // 저장 후 반환될 객체 설정
        Mockito.when(passwordEncoder.encode("password123")).thenReturn("password123");

        // when: createMember 메서드 실행
        String result = memberService.createMember(memberFormDto);

        // Then: 회원이 정상적으로 저장되었는지 확인
        assertEquals("success", result);

    }

    @Test
    void testCreateMember_ExistingEmail() {
        // Given: 이미 존재하는 이메일로 회원 가입을 시도
        MemberFormDto memberFormDto = MemberFormDto.builder()
                .name("test2")
                .email("test@email.com")
                .password("password123")
                .address("서울시 종로구")
                .build();

        Mockito.when(memberRepository.findByEmail(memberFormDto.getEmail()))
                .thenReturn(Optional.of(new Member(1L, "test", "test@email.com", "password123", "서울시 중구", Role.ADMIN)));

        // When & Then: 이미 사용 중인 이메일로 가입 시 IllegalStateException 발생
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            memberService.createMember(memberFormDto);
        });

        assertEquals("사용중인 이메일 입니다.", exception.getMessage());

    }


//    @AfterEach
//    void tearDown() {
//        memberRepository.deleteAll();
//    }


}

