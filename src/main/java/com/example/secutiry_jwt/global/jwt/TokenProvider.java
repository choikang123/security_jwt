package com.example.secutiry_jwt.global.jwt;

import com.example.secutiry_jwt.domain.member.entity.Member;
import com.example.secutiry_jwt.global.config.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Collections;
import java.util.Date;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class TokenProvider {
    //토큰을 생성하고 올바른 토큰인지 유효성 검사를 하고, 토큰에서 필요한 정보를 가져오는 클래스
    private final JwtProperties jwtProperties;

    public String generateToken(Member member, Duration expiredAt) {
        Date now = new Date();
        return makeToken(new Date(now.getTime() + expiredAt.toMillis()), member);
    }
    //jwt 토큰 생성 메서드
    private String makeToken(Date expiry, Member member) {
        Date now = new Date();

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE) //헤더 타입을 jwt로 설정
                //헤더에는 jwt타입과 서명 알고리즘 지정
                .setIssuer(jwtProperties.getIssuer())
                //토큰에 대한 정보를 담기 위한 클레임들 선택적 사용 -- 클레임들은 페이로드에 담긴다
                .setIssuedAt(now)
                .setExpiration(expiry)
                .setSubject(member.getEmail())
                .claim("id", member.getId())
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecretKey()) //HS256방식으로 비밀키 값을 암호화
                .compact();
    }

    //JWT 토큰 유효성 검증 메서드
    public boolean validToken(String token) {
        try {
            Jwts.parser()
                    // 서명 복호화 과정이며 서명은 헤더와 페이로드를 각각 base64 인코딩 후 합쳐서 서명알고리즘과 비밀키를
                    // 통해 해쉬값으로 만들어진다
                    .setSigningKey(jwtProperties.getSecretKey())
                    .parseClaimsJws(token); //
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // 토큰 기반으로 인증 정보를 가져오는 메서드 == 토큰 제목과 토큰 기반으로 인증 정보를 생성
    public Authentication getAuthentication(String token) {
        Claims claims = getClaims(token);
        Set<SimpleGrantedAuthority> authorities = Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));

        return new UsernamePasswordAuthenticationToken(new User(claims.getSubject
                (), "", authorities), token, authorities);
    }

    // 토큰 기반으로 유저 id를 가져오는 메서드
    public Long getUserId(String token) {
        Claims claims = getClaims(token);
        return claims.get("id", Long.class);
    }

    private Claims getClaims(String token) {
        return Jwts.parser() // 클레임 조회
                .setSigningKey(jwtProperties.getSecretKey())
                .parseClaimsJws(token)
                .getBody();
    }






}
