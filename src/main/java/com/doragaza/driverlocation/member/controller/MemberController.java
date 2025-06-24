package com.doragaza.driverlocation.member.controller;

import com.doragaza.driverlocation.authority.config.JwtToken;
import com.doragaza.driverlocation.authority.util.JwtUtil;
import com.doragaza.driverlocation.member.domain.LoginData;
import com.doragaza.driverlocation.member.domain.Member;
import com.doragaza.driverlocation.member.domain.MemberInfoView;
import com.doragaza.driverlocation.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/member")
public class MemberController {
    private final JwtUtil jwtUtil;
    private final MemberService memberService;

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> loginCheck(@RequestBody LoginData request) {
        if (request.getMemberId().isBlank() || request.getMemberPw().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                    "success", false,
                    "message", "아이디 또는 비밀번호가 비어있습니다."
            ));
        }
        String memberNo = memberService.loginGetMemberNo(request.getMemberId(), request.getMemberPw());
        MemberInfoView member = memberService.findMemberInfo(memberNo);  // ✅ 뷰 객체 사용
        String token = jwtUtil.generateToken(memberNo, member.getMemberAuthority());

        // 여기까지만 구성 (응답 반환 X)
        return null; // 👈 이후 응답 구성 예정
    }





}
