package com.sparta.trello.domain.deck.repository;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.trello.domain.board.entity.Board;
import com.sparta.trello.domain.deck.Entity.Deck;
import com.sparta.trello.domain.deck.Entity.QDeck;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class DeckRepositoryImpl implements DeckQueryRepository{
//    private final EntityManager em;
//    private final JPAQueryFactory query;
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Long findByCountBoardIdDeck(Board board) {

        QDeck deck = QDeck.deck;

        JPAQuery<Long> query = jpaQueryFactory.select(deck.count())
                .from(deck)
                .where(deck.board.eq(board));

        Long total = query.fetchOne();

        return Optional.ofNullable(total).orElse(0L);
    }
}
