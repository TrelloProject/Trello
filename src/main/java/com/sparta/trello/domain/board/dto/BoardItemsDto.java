package com.sparta.trello.domain.board.dto;

import com.sparta.trello.domain.card.dto.CardDto;
import com.sparta.trello.domain.deck.dto.DeckDto;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BoardItemsDto {

    private DeckDto deck;
    private List<CardDto> cards;
}