package com.sparta.trello.exception.custom.user.detail;

import com.sparta.trello.exception.custom.user.UserCodeEnum;
import com.sparta.trello.exception.custom.user.UserException;

public class UserWithdrawnException extends UserException {

    public UserWithdrawnException(UserCodeEnum userCodeEnum) {
        super(userCodeEnum);
    }
}