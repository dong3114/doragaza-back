package com.doragaza.driverlocation.authority.util;

import com.doragaza.driverlocation.authority.config.SpringSecurityConfig;
import com.doragaza.driverlocation.handler.exception.FilterExceptionHandler;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final FilterExceptionHandler filterExceptionHandler;

    public JwtAuthenticationFilter(JwtUtil jwtUtil, FilterExceptionHandler filterExceptionHandler){
        this.jwtUtil = jwtUtil;
        this.filterExceptionHandler = filterExceptionHandler;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                            HttpServletResponse response,
                            FilterChain filterChain) throws ServletException, IOException {
        System.out.println("[JwtAuthenticationFilter] 요청 시작" + request.getMethod() + " " + request.getRequestURI());
        String requestURI = request.getRequestURI();
        String method = request.getMethod();
        // 1. OPTIONS 요청일때 필터 통과 (CORS 해결)
        if(method.equalsIgnoreCase("OPTIONS")) {
            System.out.println("[JwtAuthenticationFilter] OPTIONS 요청 필터 통과");
            response.setStatus(HttpServletResponse.SC_OK);
            return;
        }
        // 인증 예외 객체 생성
        List<String> authWhiteList = SpringSecurityConfig.getAuthWhiteList();
        // 2. 인증 예외 요청이면 필터 통과
        if(authWhiteList.stream().anyMatch(p -> requestURI.matches(p.replace("**", ".*")))){
            System.out.println("🛠 [JwtAuthenticationFilter] 인증 예외 경로 접근: " + requestURI);
            filterChain.doFilter(request, response);
            return;
        }
        // 3. 헤더 검사
        String authorizationHeader = request.getHeader("Authorization");
        if(authorizationHeader == null || !authorizationHeader.startsWith("Bearer")){
            // 인증 정보 없음 401 에러
            filterExceptionHandler.handleAuthenticationFailure(response, "토큰이 없거나 Bearer 타입이 아닙니다.");
            return;
        }
        String token = authorizationHeader.replace("Bearer", "");
        System.out.println("🔎 [JwtAuthenticationFilter] 토큰 추출 완료: " + token);
        // 4. 유효성 검증
        if(!jwtUtil.validateToken(token)){
            System.out.println("🚨 [JwtAuthenticationFilter] 인증 실패 - 잘못된 토큰");
            filterExceptionHandler.handleAuthenticationFailure(response, "인증 실패");
            return;
        }
        int roleNumber = jwtUtil.extractRoleNumber(token);
        String role = jwtUtil.extractRoleName(token);
        String memberNo = jwtUtil.extractMemberNo(token);

        System.out.println("✅ [JwtAuthenticationFilter] 인증 성공 - 사용자: " + memberNo + " / Level: " + roleNumber);
        // 5. 인증 객체 설정 및 정보 추출
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(
                        memberNo,
                        null,
                        List.of(new SimpleGrantedAuthority(role))
                );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }


}
