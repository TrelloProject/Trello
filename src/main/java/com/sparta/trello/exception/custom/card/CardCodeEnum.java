package com.sparta.trello.exception.custom.card;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CardCodeEnum {
    CARD_NOT_FOUND(HttpStatus.NOT_FOUND, "카드를 찾을 수 없습니다."),
    CARD_NO_PERMISSION(HttpStatus.FORBIDDEN, "카드의 작성자가 아닙니다.");

    private final HttpStatus httpStatus;
    private final String message;
}