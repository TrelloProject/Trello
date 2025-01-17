package com.sparta.trello.exception.custom.board.detail;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum BoardCodeEnum {
    BOARD_NOT_FOUND(HttpStatus.NOT_FOUND, "보드가 존재하지 않습니다."),



    ;
    private final HttpStatus httpStatus;
    private final String message;
}
