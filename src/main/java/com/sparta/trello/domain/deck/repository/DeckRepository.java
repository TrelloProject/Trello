package com.sparta.trello.domain.deck.repository;

import com.sparta.trello.domain.board.entity.Board;
import com.sparta.trello.domain.deck.entity.Deck;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeckRepository extends JpaRepository<Deck, Long>, DeckQueryRepository {

    List<Deck> findAllByBoardId(Long boardId);

    List<Deck> findByBoard(Board board);

    Optional<Deck> findByNextIdAndBoardId(Long nextId, Long boardId);
}