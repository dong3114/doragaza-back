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
    private int memberAuthority;

    // driver 테이블
    private String driverNo;
}
