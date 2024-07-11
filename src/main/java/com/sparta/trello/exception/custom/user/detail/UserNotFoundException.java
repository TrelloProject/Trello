package com.sparta.trello.exception.custom.user.detail;

import com.sparta.trello.exception.custom.user.UserCodeEnum;
import com.sparta.trello.exception.custom.user.UserException;

public class UserNotFoundException extends UserException {

    public UserNotFoundException(UserCodeEnum userCodeEnum) {
        super(userCodeEnum);
    }
}