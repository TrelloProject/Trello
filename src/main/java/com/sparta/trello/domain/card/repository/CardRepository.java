package com.sparta.trello.domain.card.repository;

import com.sparta.trello.domain.card.entity.Card;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card, Long> {

    Card findCardByNextId(Long cardId);

    Optional<Card> findTopByDeckIdOrderByIdDesc(Long deckId);

    List<Card> findAllByDeckId(Long deckId);
}