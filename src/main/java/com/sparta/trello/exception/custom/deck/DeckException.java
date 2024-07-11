package com.sparta.trello.exception.custom.deck;

import com.sparta.trello.exception.custom.deck.detail.DeckCodeEnum;
import lombok.Getter;

@Getter
public class DeckException extends RuntimeException{
    private final DeckCodeEnum deckCodeEnum;

    public DeckException (DeckCodeEnum deckCodeEnum){
        super(deckCodeEnum.getMessage());
        this.deckCodeEnum = deckCodeEnum;
    }
}
