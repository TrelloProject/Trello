package com.sparta.trello.exception.custom.card.detail;

import com.sparta.trello.exception.custom.card.CardCodeEnum;
import com.sparta.trello.exception.custom.card.CardException;

public class CardNoPermissionException extends CardException {

    public CardNoPermissionException(CardCodeEnum cardCodeEnum) {
        super(cardCodeEnum);
    }
}