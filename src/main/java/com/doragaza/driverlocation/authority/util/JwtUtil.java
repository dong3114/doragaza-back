package com.doragaza.driverlocation.authority.util;

import com.doragaza.driverlocation.authority.config.JwtToken;
import com.doragaza.driverlocation.member.domain.Member;
import com.doragaza.driverlocation.member.mapper.MemberMapper;
import com.doragaza.driverlocation.member.service.MemberService;
import io.jsonwebtoken.*;
import io.swagger.models.auth.In;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class JwtUtil {
    @Autowired
    private final JwtToken jwtToken;
    private final MemberService memberService;

    /**
     * 토큰 발급 정보
     * @param memberNo 회원코드
     * role 열거형 권한 정보
     * @return jwtToken
     */
    public String generateToken(String memberNo){
        Map<String, Object> claims = memberService.findMNumENum(memberNo);
        String roleName =  RoleName.getRoleName((Integer) claims.get("level"));
        return Jwts.builder()
                .setSubject(memberNo)
                .claim("level", claims.get("level"))
                .claim("role", roleName)
                .claim("enterpriseNo", claims.get("enterprise"))
                .setIssuer(jwtToken.getIssuer())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtToken.getExpiration()))
                .signWith(SignatureAlgorithm.HS256, jwtToken.getSecret())
                .compact();
    }

    /**
     * ✅ JWT 갱신 (토큰 만료시간 연장)
     */
    public String refreshToken(String oldToken){
        if(!validateToken(oldToken)){
            throw new RuntimeException("토큰이 유효하지 않습니다.");
        }
        String memberNo = extractMemberNo(oldToken);
        return generateToken(memberNo);
    }

    /**
     * memberNo 추출
     */
    public String extractMemberNo(String token) {
        return getClaims(token).getSubject();
    }
    /**
     * 토큰 만료 시간 반환
     */
    public long getExpirationTime() {
        return jwtToken.getExpiration();
    }
    /**
     * roleName 추출
     */
    public String extractRoleName(String token) {
        return getClaims(token).get("role", String.class);
    }
    /**
     * roleNumber 추출
     */
    public Integer extractRoleNumber(String token) {
        return getClaims(token).get("level", Integer.class);
    }
    /**
     * enterpriseNo 추출
     */
    public String extractEnterpriseNo(String token) {
        return getClaims(token).get("enterpriseNo", String.class);
    }
    /**
     * 토큰의 유효성 검사
     */
    public boolean validateToken(String token){
        try {
            getClaims(token);
            return true;
        } catch (ExpiredJwtException e) {
            System.out.println("JWT 만료됨: " + e.getMessage());
        } catch (UnsupportedJwtException e) {
            System.out.println("지원되지 않는 JWT: " + e.getMessage());
        } catch (SignatureException e) {
            System.out.println("JWT 서명 오류: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("JWT 잘못된 인자: " + e.getMessage());
        }
        return false;
    }
    /**
     * 토큰 파싱 공통 메서드
     * @param token
     * @return 토큰 파싱 데이터
     */
    private Claims getClaims(String token){
        return Jwts.parser()
                .setSigningKey(jwtToken.getSecret())
                .parseClaimsJws(token.replace("Bearer", "").trim())
                .getBody();
    }
}
