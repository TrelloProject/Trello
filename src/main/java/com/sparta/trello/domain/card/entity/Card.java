package com.sparta.trello.domain.card.entity;

import com.sparta.trello.common.TimeStampEntity;
import com.sparta.trello.domain.deck.entity.Deck;
import com.sparta.trello.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "card")
public class Card extends TimeStampEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OnDelete(action = OnDeleteAction.CASCADE) //삭제를 테스트 위해 임시로 적어둠
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "deck_id", nullable = false)
    private Deck deck;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private Long nextId;

    private String title;

    private String description;

    private LocalDate startDate;

    private LocalDate dueDate;
}