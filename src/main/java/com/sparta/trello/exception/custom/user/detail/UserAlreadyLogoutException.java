package com.sparta.trello.exception.custom.user.detail;

import com.sparta.trello.exception.custom.user.UserCodeEnum;
import com.sparta.trello.exception.custom.user.UserException;

public class UserAlreadyLogoutException extends UserException {

    public UserAlreadyLogoutException(UserCodeEnum userCodeEnum) {
        super(userCodeEnum);
    }
}