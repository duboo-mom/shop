package com.shop.shop.member.entity;

import com.shop.shop.constant.Role;
import com.shop.shop.member.dto.MemberFormDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="member")
@Getter
@Setter
@ToString
public class Member {

    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @Column(unique = true)
    private String email;

    private String password;

    private String address;

    private Role role;

    public static Member createMember(MemberFormDto memberFormDto) {
        Member member = new Member();
        member.setName(memberFormDto.getName());
        member.setEmail(memberFormDto.getEmail());
        member.setAddress(memberFormDto.getAddress());
        member.setPassword(memberFormDto.getPassword()); // 나중에 암호화 필요
        member.setRole(Role.USER);
        return member;
    }

}
