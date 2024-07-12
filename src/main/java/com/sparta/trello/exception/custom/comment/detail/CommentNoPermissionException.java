package com.sparta.trello.exception.custom.comment.detail;

import com.sparta.trello.exception.custom.comment.CommentCodeEnum;
import com.sparta.trello.exception.custom.comment.CommentException;

public class CommentNoPermissionException extends CommentException {

    public CommentNoPermissionException(CommentCodeEnum commentCodeEnum) {
        super(commentCodeEnum);
    }
}