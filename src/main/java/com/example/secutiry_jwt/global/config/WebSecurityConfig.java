package com.example.secutiry_jwt.global.config;

import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {
    private final UserDetailsService userService;

    //특정 http 요청에 대한 웹 기반 보안 구성
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        return http
//                .authorizeHttpRequests(auth->auth
//                        .requestMatchers(
//                                new AntPathRequestMatcher("/login")
//                                new AntPathRequestMatcher("/signup")
//                                new AntPathRequestMatcher("/member")
//                        ).PermitAll()
//                .anyRequest().auth)

    }
}
