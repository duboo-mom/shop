package com.shop.shop.member.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
@Getter
@Builder
public class MemberFormDto {

    @NotBlank(message = "이름은 필수 항목입니다.")
    private String name;

    @Email
    @NotBlank(message = "이메일은 필수 항목입니다.")
    private String email;

    @NotBlank(message = "비밀번호는 필수 항목입니다.")
    @Size(min = 8, max = 16, message = "비밀번호는 8자리 이상, 16자리 이하로 입력해주세요.")
    private String password;

    private String address;

}
