package com.doragaza.driverlocation.member.mapper;

import com.doragaza.driverlocation.member.domain.Member;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MemberMapper {
    /**
     * 회원 가입
     *
     * @param member 회원 객체
     * @return 삽입 성공 1, 실패 0
     */
    int insertMember(Member member);
    /**
     * 로그인
     */
    Integer loginCheck(@Param("memberId") String memberId, @Param("memberPw") String memberPw);
    // ID를 통해 회원 번호 조회
    String getMemberId(@Param("memberId") String memberId);
    Member getMemberInfo(@Param("memberNo") String memberNo);
    String findEnterpriseNo(@Param("memberNo") String memberNo);
    /**
     * 회원가입 유효성검증
     */
    int validateId(@Param("memberId") String memberId);
    int checkMemberPhone(@Param("memberPhone") String memberPhone);
    int checkMemberEmail(@Param("memberEmail") String memberEmail);
}