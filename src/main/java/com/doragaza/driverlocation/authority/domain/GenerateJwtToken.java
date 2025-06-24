package com.doragaza.driverlocation.authority.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GenerateJwtToken {
    private final String token;
    private final String memberNo;
    private final Integer roleNumber;
    private final String roleName;
    private final long expires;
}
