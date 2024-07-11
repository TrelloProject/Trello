package com.sparta.trello.auth.jwt;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum JwtCodeEnum {
    INVALID_JWT_SIGNATURE(HttpStatus.UNAUTHORIZED, "유효하지 않는 JWT 서명 입니다."),
    EXPIRED_JWT_TOKEN(HttpStatus.UNAUTHORIZED, "만료된 토큰입니다. 다시 로그인을 해주세요."),
    UNSUPPORTED_JWT_TOKEN(HttpStatus.FORBIDDEN, "지원되지 않는 JWT 토큰 입니다."),
    EMPTY_JWT_CLAIMS(HttpStatus.BAD_REQUEST, "잘못된 JWT 토큰입니다."),
    JWT_NOT_FOUND(HttpStatus.UNAUTHORIZED, "로그인이 필요한 기능입니다.");

    private final HttpStatus httpStatus;
    private final String message;
}