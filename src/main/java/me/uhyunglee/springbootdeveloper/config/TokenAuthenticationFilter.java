package me.uhyunglee.springbootdeveloper.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.uhyunglee.springbootdeveloper.config.jwt.TokenProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
public class TokenAuthenticationFilter extends OncePerRequestFilter {
    private final TokenProvider tokenProvider;
    private final static String HEADER_AUTHORIZATION = "Autorization";
    private final static String TOKEN_PREFIX = "Bearer ";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws
            ServletException, IOException{
        // 요청 헤더의 Authorization 키의 값 조회
        String authorizationHeader = request.getHeader(HEADER_AUTHORIZATION);
        // 가져온 값에서 접두사 제거
        String token = getAccessToken(authorizationHeader);
        // 가져온 토큰이 유효한지 확인하고, 유효할 때는 인증정보를 설정
        if(tokenProvider.validToken(token)){
            Authentication authentication = tokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }
    private String getAccessToken(String authorizationHeader){
        if(authorizationHeader != null && authorizationHeader.startsWith(TOKEN_PREFIX)){
            return authorizationHeader.substring(TOKEN_PREFIX.length());
        }
        return null;
    }
}
