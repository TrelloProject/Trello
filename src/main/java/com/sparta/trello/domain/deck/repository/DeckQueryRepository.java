package com.sparta.trello.domain.deck.repository;

import com.sparta.trello.domain.board.entity.Board;
import com.sparta.trello.domain.deck.Entity.Deck;

import java.util.Optional;

public interface DeckQueryRepository {
    Long findByCountBoardIdDeck(Board board);
}
