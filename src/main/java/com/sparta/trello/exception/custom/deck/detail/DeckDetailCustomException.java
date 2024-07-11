package com.sparta.trello.exception.custom.deck.detail;

import com.sparta.trello.exception.custom.deck.DeckException;

public class DeckDetailCustomException extends DeckException {
    public DeckDetailCustomException(DeckCodeEnum deckCodeEnum){
        super(deckCodeEnum);
    }
}
