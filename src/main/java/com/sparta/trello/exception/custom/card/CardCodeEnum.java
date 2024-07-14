package com.sparta.trello.exception.custom.card;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CardCodeEnum {
    CARD_NOT_FOUND(HttpStatus.NOT_FOUND, "카드를 찾을 수 없습니다."),
    CARD_INDEX_OUT_OF_BOUNDS_ERROR(HttpStatus.BAD_REQUEST, "카드가 인덱스의 범위를 벗어났습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}