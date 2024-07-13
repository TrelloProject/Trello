package com.sparta.trello.exception.custom.card.detail;

import com.sparta.trello.exception.custom.card.CardCodeEnum;
import com.sparta.trello.exception.custom.card.CardException;

public class CardIndexOutOfBoundsException extends CardException {

    public CardIndexOutOfBoundsException(CardCodeEnum cardCodeEnum) {
        super(cardCodeEnum);
    }
}