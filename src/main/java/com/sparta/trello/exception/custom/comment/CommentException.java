package com.sparta.trello.exception.custom.comment;

import lombok.Getter;

@Getter
public class CommentException extends RuntimeException {

    private final CommentCodeEnum commentCodeEnum;

    public CommentException(CommentCodeEnum commentCodeEnum) {
        super(commentCodeEnum.getMessage());
        this.commentCodeEnum = commentCodeEnum;
    }
}