package com.sparta.trello.exception.custom.user.detail;

import com.sparta.trello.exception.custom.user.UserCodeEnum;
import com.sparta.trello.exception.custom.user.UserException;

public class DuplicatedUsernameUserException extends UserException {

    public DuplicatedUsernameUserException(UserCodeEnum userCodeEnum) {
        super(userCodeEnum);
    }
}