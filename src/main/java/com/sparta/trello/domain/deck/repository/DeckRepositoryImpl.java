package com.sparta.trello.domain.deck.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class DeckRepositoryImpl implements DeckQueryRepository{
    private final EntityManager em;
    private final JPAQueryFactory query;
}
