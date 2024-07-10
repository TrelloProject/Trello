package com.sparta.trello.exception.custom.user;

import lombok.Getter;

@Getter
public class UserException extends RuntimeException {

    private final UserCodeEnum userCodeEnum;

    public UserException(UserCodeEnum userCodeEnum) {
        super(userCodeEnum.getMessage());
        this.userCodeEnum = userCodeEnum;
    }
}