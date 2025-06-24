package com.doragaza.driverlocation.member.service;

import java.util.Map;

public interface MemberService {
    Boolean loginCheck(String memberId, String memberPw);
    Map<String, Object> findMemberInfo(String memberNo);

}
