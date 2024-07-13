package com.sparta.trello.exception.custom.jwt.detail;

import com.sparta.trello.exception.custom.jwt.JwtCodeEnum;
import com.sparta.trello.exception.custom.jwt.JwtException;

public class JwtAlreadyRemoveException extends JwtException {

    public JwtAlreadyRemoveException(JwtCodeEnum jwtCodeEnum) {
        super(jwtCodeEnum);
    }
}