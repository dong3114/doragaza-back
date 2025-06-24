package com.doragaza.driverlocation.member.service;

import com.doragaza.driverlocation.member.domain.MemberInfoView;
import com.doragaza.driverlocation.member.mapper.MemberMapper;
import com.doragaza.driverlocation.member.domain.Member;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberMapper memberMapper;

    public String loginGetMemberNo(String memberId, String memberPw) {
        if (memberId == null || memberPw == null || memberId.isBlank() || memberPw.isBlank()) {
            throw new IllegalArgumentException("회원정보가 누락되었습니다.");
        }
        String memberNo = memberMapper.getLoginData(memberId, memberPw);

        if (memberNo == null || memberNo.isBlank()) {
            throw new IllegalArgumentException("로그인 정보가 일치하지 않습니다.");
        }
        return memberNo;
    }



    // 토큰 발급 시점에서 회원 정보 로드
    public MemberInfoView findMemberInfo(String memberNo){
        if (memberNo == null || memberNo.isBlank()) {
            throw new IllegalArgumentException("[Service] 회원번호가 누락되었습니다.");
        }
        Member member = memberMapper.getMemberInfo(memberNo);
        return Member.from(member);
    }

}
