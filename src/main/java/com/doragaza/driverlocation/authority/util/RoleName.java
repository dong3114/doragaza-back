package com.doragaza.driverlocation.authority.util;

public class RoleName {
    public static String getRoleName(int roleNumber){
        return switch (roleNumber) {
            case 1 -> "ROLE_USER";
            case 2 -> "ROLE_DRIVER";
            case 3 -> "ROLE_COMPANY_ADMIN";
            case 4 -> "ROLE_ADMIN";
            default -> throw new IllegalArgumentException("알수없는 권한 레벨");
        };
    }
}
