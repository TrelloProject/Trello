package com.sparta.trello.exception.custom.boardMember.detail;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum BoardMemberCodeEnum {
    BOARD_MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "보드맴버가 존재하지 않습니다."),
    BOARD_MEMBER_FORBIDDEN(HttpStatus.FORBIDDEN, "보드의 권한이 없습니다."),


            ;
    private final HttpStatus httpStatus;
    private final String message;
}
