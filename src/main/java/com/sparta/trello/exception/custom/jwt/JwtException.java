package com.sparta.trello.exception.custom.jwt;

import lombok.Getter;

@Getter
public class JwtException extends RuntimeException {

    private final JwtCodeEnum jwtCodeEnum;

    public JwtException(JwtCodeEnum jwtCodeEnum) {
        super(jwtCodeEnum.getMessage());
        this.jwtCodeEnum = jwtCodeEnum;
    }
}