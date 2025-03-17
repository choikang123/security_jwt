package com.example.secutiry_jwt.global.jwt;

import com.example.secutiry_jwt.domain.member.entity.Member;
import com.example.secutiry_jwt.domain.member.service.MemberService;
import com.example.secutiry_jwt.global.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;

@RequiredArgsConstructor
@Service
public class TokenService {
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final MemberService memberService;
    private final RefreshTokenService refreshTokenService;

    public String createNewAccessToken(String refreshToken) {
        // 토큰 유효성 검사에 실패하면 예외 발생
        if(!tokenProvider.validToken(refreshToken)) {
            throw new IllegalArgumentException("unexpected refreshToken");
        }

        Long memberId = refreshTokenService.findByToken(refreshToken).getId();
        Member member = memberService.findById(memberId);

        return tokenProvider.generateToken(member, Duration.ofHours(2));
    }
}
