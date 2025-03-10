package com.example.secutiry_jwt.global.config;

import com.example.secutiry_jwt.global.jwt.TokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
// 이 필터는 액세스 토큰값이 담긴 Authorization 헤더값을 가져온 뒤 액세스 토큰이 유효하다면 인증 정보를 실행
public class TokenAuthenticationFilter extends OncePerRequestFilter {
    // 로그인 후 사용자가 API를 호출할 때마다 이 필터가 작동
    private final TokenProvider tokenProvider;
    private final static String HEADER_AUTHORIZATION = "Authorization";
    private final static String TOKEN_PREFIX = "Bearer ";

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        // 요청 헤더의 Authorization 키 값을 조회
        String authorizationHeader = request.getHeader(HEADER_AUTHORIZATION);

        // 가져온 값에서 접두사 제거
        String token = getAccessToken(authorizationHeader); // ✅ 메서드 이름 수정

        // 토큰이 유효한지 확인하고, 유효하면 인증 정보 설정
        String name = null;
        if (tokenProvider.validToken(token)) {
            Authentication authentication = tokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication); // contextHolder에 사용자 정보 설정
            name = authentication.getName();
        }

        // 게스트 계정이면 GET, DELETE 메서드 외에는 차단
        if (name != null && name.startsWith("&guest")) {
            if (request.getMethod().equalsIgnoreCase("OPTIONS")
                    || request.getMethod().equalsIgnoreCase("PUT")
                    || request.getMethod().equalsIgnoreCase("POST")
                    || request.getMethod().equalsIgnoreCase("TRACE")
                    || request.getMethod().equalsIgnoreCase("PATCH")) {
                response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
                return;
            }
        }

        // 필터 체인 계속 진행
        filterChain.doFilter(request, response);
    }

    private String getAccessToken(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith(TOKEN_PREFIX)) {
            return authorizationHeader.substring(TOKEN_PREFIX.length()).trim(); // 공백 제거 추가
        }
        return null;
    }
}

