package com.shop.shop.member.service;

import com.shop.shop.member.dto.MemberFormDto;
import com.shop.shop.member.entity.Member;
import com.shop.shop.member.repository.MemberRepository;
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

    @Test
    @DisplayName("회원 가입 성공 테스트")
    void saveMemberSuccess() {
        //given
        MemberFormDto memberFormDto = newMemberFormDto();
        Member newMember = Member.createMember(memberFormDto);

        // Mock 설정
        Mockito.when(memberRepository.findByEmail(memberFormDto.getEmail()))
                .thenReturn(null); // 이메일 중복이 없다고 가정
        Mockito.when(memberRepository.save(Mockito.any(Member.class)))
                .thenReturn(newMember); // 저장 후 반환될 객체 설정

        //when
        Member result = memberService.saveMember(newMember);

        //then
        assertNotNull(result);
        assertEquals("테스트", result.getName());
        assertEquals("서울시 어쩌구 저쩌구", result.getAddress());

        // Repository 메서드 호출 확인
        Mockito.verify(memberRepository, times(1)).findByEmail(memberFormDto.getEmail());
        Mockito.verify(memberRepository, times(1)).save(newMember);

    }

    private MemberFormDto newMemberFormDto() {

        MemberFormDto memberFormDto = new MemberFormDto();
        memberFormDto.setName("테스트");
        memberFormDto.setPassword("123456");
        memberFormDto.setEmail("email@email.com");
        memberFormDto.setAddress("서울시 어쩌구 저쩌구");
        return memberFormDto;

    }


}