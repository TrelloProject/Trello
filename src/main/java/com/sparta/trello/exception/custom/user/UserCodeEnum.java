package com.sparta.trello.exception.custom.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserCodeEnum {
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "없다.");

    private final HttpStatus httpStatus;
    private final String message;
}