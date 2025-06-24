package com.doragaza.driverlocation.member.service;

import com.doragaza.driverlocation.member.domain.MemberInfoView;

public interface MemberService {
    String loginGetMemberNo(String memberId, String memberPw);
    MemberInfoView findMemberInfo(String memberNo);
}
