package com.shop.shop.member.repository;

import com.shop.shop.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    // 기본적인 CRUD 메서드들은 JpaRepository에 이미 정의되어 있음
    // JpaRepository를 상속하여 기본적인 CRUD 기능 제공
    // 예: save(), findById(), findAll(), deleteById() 등

    Optional<Member> findByEmail(String email);

}
