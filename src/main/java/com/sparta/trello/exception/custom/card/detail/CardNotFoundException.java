package com.sparta.trello.exception.custom.card.detail;

import com.sparta.trello.exception.custom.card.CardCodeEnum;
import com.sparta.trello.exception.custom.card.CardException;

public class CardNotFoundException extends CardException {

    public CardNotFoundException(CardCodeEnum cardCodeEnum) {
        super(cardCodeEnum);
    }
}