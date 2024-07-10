package com.sparta.trello.domain.user.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserAuthRole {
    ROLE_USER("USER"),  // 사용자 권한
    ROLE_ADMIN("ADMIN")  // 관리자 권한
    ;
    private final String role;
}