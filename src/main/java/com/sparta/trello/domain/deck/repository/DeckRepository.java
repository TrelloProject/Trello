package com.sparta.trello.domain.deck.repository;

import com.sparta.trello.domain.deck.Entity.Deck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeckRepository extends JpaRepository<Deck, Long>, DeckQueryRepository {
}
