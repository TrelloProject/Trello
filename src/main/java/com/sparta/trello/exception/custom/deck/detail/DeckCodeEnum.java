package com.sparta.trello.exception.custom.deck.detail;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum DeckCodeEnum {
    DECK_NOT_FOUND(HttpStatus.NOT_FOUND, "DECK이 존재하지 않습니다."),



    ;
    private final HttpStatus httpStatus;
    private final String message;
}
