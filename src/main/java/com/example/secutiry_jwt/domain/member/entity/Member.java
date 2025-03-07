package com.example.secutiry_jwt.domain.member.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "members")
@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    // 사용자 이메일(고유)
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Builder
    public Member(String email) {
        this.email = email;
    }
}
