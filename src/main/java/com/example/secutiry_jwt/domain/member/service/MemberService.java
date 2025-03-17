package com.example.secutiry_jwt.domain.member.service;

import com.example.secutiry_jwt.domain.member.entity.Member;
import com.example.secutiry_jwt.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberService {
  private final MemberRepository memberRepository;

  public Member findById(Long memberId) {
      return memberRepository.findById(memberId)
              .orElseThrow(()->new IllegalArgumentException("unexpected memberId"));
  }
}
