package com.sparta.trello.exception.custom.card;

import lombok.Getter;

@Getter
public class CardException extends RuntimeException {

    private final CardCodeEnum cardCodeEnum;

    public CardException(CardCodeEnum cardCodeEnum) {
        super(cardCodeEnum.getMessage());
        this.cardCodeEnum = cardCodeEnum;
    }
}