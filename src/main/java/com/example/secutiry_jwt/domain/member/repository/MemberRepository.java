package com.example.secutiry_jwt.domain.member.repository;

import com.example.secutiry_jwt.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

}
