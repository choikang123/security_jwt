package com.example.secutiry_jwt.config.jwt;

import com.example.secutiry_jwt.domain.member.entity.Member;
import com.example.secutiry_jwt.domain.member.repository.MemberRepository;
import com.example.secutiry_jwt.global.config.JwtProperties;
import com.example.secutiry_jwt.global.jwt.TokenProvider;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Duration;
import java.util.Date;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Slf4j
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
    @Test
    void validToken() {
        //given
        String token = JwtFactory.builder()
                .expiration(new Date(new Date().getTime() - Duration.ofDays(7).toMillis()))
                .build()
                .createToken(jwtProperties);
        //when
        boolean result = tokenProvider.validToken(token);
        //then
        assertThat(result).isFalse();
    }

    @DisplayName("validToken(): 유효한 토큰인 때에 유효성 검사에 성공한다")
    @Test
    void validToken_validToken() {
        //given
        String token = JwtFactory.withDefaultValues()
                .createToken(jwtProperties);
        //when
        boolean result = tokenProvider.validToken(token);
        assertThat(result).isTrue();
    }

    @DisplayName("getAuthentication(): 토큰 기반으로 인증 정보를 가져올 수 있다")
    @Test
    void getAuthentication() {
//        //given
//        String memberEmail = "member@gmail.com";
//        String token = JwtFactory.builder()
//                .subject(memberEmail)
//                .build()
//                .createToken(jwtProperties);
//        //when
//        Authentication authentication = tokenProvider.getAuthentication(token);
//        //then
//        assertThat((UserDetails)authentication.getPrincipal()).getMemberName()).IsEqualTo(memberEmail);
//        ;
    }

    @DisplayName("getMemberId(): 토큰으로 멤버 ID를 가져올 수 있다.")

}
