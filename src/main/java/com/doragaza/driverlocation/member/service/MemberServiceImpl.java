package com.doragaza.driverlocation.member.service;

import com.doragaza.driverlocation.authority.util.JwtUtil;
import com.doragaza.driverlocation.member.mapper.MemberMapper;
import com.doragaza.driverlocation.member.domain.Member;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final JwtUtil jwtUtil;
    private final MemberMapper memberMapper;

    public Boolean loginCheck(String memberId, String memberPw){
        if(memberId == null || memberPw == null){
            throw new IllegalArgumentException("[Service]회원정보가 누락되었습니다.");
        }

        Integer userInput = memberMapper.loginCheck(memberId, memberPw);
        if(userInput == null){
            throw new IllegalArgumentException("[Service]존재 하지 않는 회원입니다.");
        }
        if(userInput == 1){
            System.out.println("[Service]로그인 성공");
            return Boolean.TRUE;
        } else {
            System.out.println("[Service]로그인 실패 회원 정보를 확인하세요.");
            return Boolean.FALSE;
        }
    }

    // 토큰 발급 시점에서 회원 정보 로드
    public Map<String, Object> findMemberInfo(String memberNo){
        if(memberNo == null){
            throw new IllegalArgumentException("[Service] 회원정보가 없습니다.");
        }
        Member memberInfo = memberMapper.getMemberInfo(memberNo);
        Map<String, Object> info = new HashMap<>();
        info.put("memberName", memberInfo.getMemberName());
        info.put("memberEmail", memberInfo.getMemberEmail());
        info.put("memberPhone", memberInfo.getMemberPhone());
        return info;
    }

}
