package com.example.secutiry_jwt.global.repository;

import com.example.secutiry_jwt.global.jwt.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByRefreshToken(String refreshToken);
    Optional<RefreshToken> findByMemberId(Long memberId);
}
