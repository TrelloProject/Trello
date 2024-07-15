package com.sparta.trello.domain.card.repository;

import com.sparta.trello.domain.card.entity.Card;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CardRepository extends JpaRepository<Card, Long> {

    Card findCardByNextId(Long cardId);

    List<Card> findAllByDeckId(Long deckId);

    @Query("SELECT c FROM Card c JOIN FETCH c.deck WHERE c.id = :cardId")
    Optional<Card> findByIdWithDeck(@Param("cardId") Long cardId);

    @Query("SELECT c FROM Card c JOIN FETCH c.user WHERE c.id = :cardId")
    Optional<Card> findByIdWithUser(@Param("cardId") Long cardId);

    @Query("SELECT c FROM Card c JOIN FETCH c.deck JOIN FETCH c.user WHERE c.id = :cardId")
    Optional<Card> findByIdWithDeckAndUser(@Param("cardId") Long cardId);
}