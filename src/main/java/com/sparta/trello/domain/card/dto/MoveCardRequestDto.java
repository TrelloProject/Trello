package com.sparta.trello.domain.card.dto;

import lombok.Getter;

@Getter
public class MoveCardRequestDto {

    private Long deckId;
    private int index;
}