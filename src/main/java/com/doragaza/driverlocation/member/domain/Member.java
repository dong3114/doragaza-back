package com.doragaza.driverlocation.member.domain;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Builder
public class Member {
    private String memberNo;
    private String memberId;
    private String memberPw;
    private String memberName;
    private String memberPhone;
    private String memberEmail;
    private Integer memberAuthority;
    // driver 테이블
    private String driverNo;

    public static MemberInfoView from(Member member) {
        return MemberInfoView.builder()
                .memberNo(member.getMemberNo())
                .memberId(member.getMemberId())
                .memberName(member.getMemberName())
                .memberPhone(member.getMemberPhone())
                .memberEmail(member.getMemberEmail())
                .memberAuthority(member.getMemberAuthority())
                .build();
        }
}
