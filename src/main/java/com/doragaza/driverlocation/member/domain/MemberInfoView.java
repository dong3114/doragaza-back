package com.doragaza.driverlocation.member.domain;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberInfoView {
    private String memberNo;
    private String memberId;
    private String memberName;
    private String memberPhone;
    private String memberEmail;
    private Integer memberAuthority;
}