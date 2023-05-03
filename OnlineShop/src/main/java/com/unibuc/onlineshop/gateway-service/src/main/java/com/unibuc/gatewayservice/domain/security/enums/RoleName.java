package com.unibuc.gatewayservice.domain.security.enums;

public enum RoleName {
    ROLE_USER, ROLE_ADMIN;

    public boolean isRoleAdmin(){
        return this == ROLE_ADMIN;
    }
}
