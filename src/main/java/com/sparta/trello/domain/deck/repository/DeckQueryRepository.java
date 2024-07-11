package com.sparta.trello.domain.deck.repository;

import com.sparta.trello.domain.board.entity.Board;

public interface DeckQueryRepository {
    Long findByCountBoardIdDeck(Board board);
}
