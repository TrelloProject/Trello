package com.sparta.trello.exception.custom.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserCodeEnum {
    NOT_FOUND(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."),
    DUPLICATED_USERNAME(HttpStatus.CONFLICT, "사용중인 ID 입니다.");

    private final HttpStatus httpStatus;
    private final String message;
}