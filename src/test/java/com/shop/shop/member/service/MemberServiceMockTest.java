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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class MemberServiceMockTest {

    @Mock
    private MemberRepository memberRepository;

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
                .thenReturn(null); // 이메일 중복이 없다고 가정

        Member savedMember = Member.builder()
                .id(1) // id는 1로 설정 (자동 생성된 값처럼)
                .name("test1")
                .email("test1@email.com")
                .password("password123")
                .address("서울시 동대문구")
                .role(Role.USER)
                .build();

        Mockito.when(memberRepository.save(Mockito.any(Member.class)))
                .thenReturn(savedMember); // 저장 후 반환될 객체 설정

        // when: createMember 메서드 실행
        Member createdMember = memberService.createMember(memberFormDto);

        // Then: 회원이 정상적으로 저장되었는지 확인
        assertNotNull(createdMember); // 생성된 멤버 객체가 null이 아님을 확인
        assertEquals("test1", createdMember.getName());
        assertEquals("test1@email.com", createdMember.getEmail()); // email 확인

        // Repository 메서드 호출 확인
        Mockito.verify(memberRepository, times(1)).findByEmail(memberFormDto.getEmail());
        Mockito.verify(memberRepository, times(1)).save(savedMember);

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

