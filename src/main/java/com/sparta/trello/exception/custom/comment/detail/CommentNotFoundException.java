package com.sparta.trello.exception.custom.comment.detail;

import com.sparta.trello.exception.custom.comment.CommentCodeEnum;
import com.sparta.trello.exception.custom.comment.CommentException;

public class CommentNotFoundException extends CommentException {

    public CommentNotFoundException(CommentCodeEnum commentCodeEnum) {
        super(commentCodeEnum);
    }
}