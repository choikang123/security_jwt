package com.example.secutiry_jwt.config.jwt;

import com.example.secutiry_jwt.domain.member.entity.Member;
import com.example.secutiry_jwt.domain.member.repository.MemberRepository;
import com.example.secutiry_jwt.global.config.JwtProperties;
import com.example.secutiry_jwt.global.jwt.TokenProvider;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class TokenProviderTest {
    @Autowired
    private TokenProvider tokenProvider;
    @Autowired
    private JwtProperties jwtProperties;
    @Autowired
    private MemberRepository memberRepository;

    //generateToken() 검증 테스트
    @DisplayName("generateToken(): 유저 정보와 만료 기간을 전달해 토큰을 만들 수 있다")
    @Test
    void generateToken() {
        //given
        Member testMember = memberRepository.save(Member.builder()
                .email("choikang@gmail.com")
                .password("test")
                .build());
        //when
        String token = tokenProvider.generateToken(testMember, Duration.ofDays(14));
        //then
        Long memberId = Jwts.parser()
                .setSigningKey(jwtProperties.getSecretKey())
                .parseClaimsJws(token)
                .getBody()
                .get("id", Long.class);

        assertThat(memberId).isEqualTo(testMember.getId());
    }

    @DisplayName("validToken(): 만료된 토큰인 때에 유효성 검사에 실패한다")

    @DisplayName("validToken(): 유효한 토큰인 때에 유효성 검사에 성공한다")

    @DisplayName("getAuthentication(): 토큰 기반으로 인증 정보를 가져올 수 있다")

    @DisplayName("getMemberId(): 토큰으로 멤버 ID를 가져올 수 있다.")
}
