package com.sparta.trello.domain.deck.dto;

import com.sparta.trello.domain.deck.entity.Deck;
import lombok.Getter;

@Getter
public class DeckDto {
    private Long id;
    private String title;

    public DeckDto(Deck deck) {
        this.id = deck.getId();
        this.title = deck.getTitle();
    }
}