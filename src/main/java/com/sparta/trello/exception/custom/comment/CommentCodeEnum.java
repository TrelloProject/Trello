package com.sparta.trello.exception.custom.comment;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CommentCodeEnum {
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "댓글을 찾을 수 없습니다."),
    COMMENT_NO_PERMISSION(HttpStatus.FORBIDDEN, "댓글의 작성자가 아닙니다.");

    private final HttpStatus httpStatus;
    private final String message;
}