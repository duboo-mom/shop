package com.shop.shop.member.entity;

import com.shop.shop.member.constant.Role;
import com.shop.shop.member.dto.MemberFormDto;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="member")
@Getter
@ToString
@NoArgsConstructor
public class Member {

    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true)
    private String email;

    private String password;

    private String address;

    private Role role;

    @Builder
    public Member(Long id, String name, String email, String password, String address, Role role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.address = address;
        this.role = role;
    }
}
