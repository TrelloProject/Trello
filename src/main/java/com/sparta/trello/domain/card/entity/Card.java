package com.sparta.trello.domain.card.entity;

import com.sparta.trello.common.TimeStampEntity;
import com.sparta.trello.domain.deck.entity.Deck;
import com.sparta.trello.domain.user.entity.User;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "card")
public class Card extends TimeStampEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @OnDelete(action = OnDeleteAction.CASCADE) //삭제를 테스트 위해 임시로 적어둠
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "deck_id", nullable = false)
    private Deck deck;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Setter
    private Long nextId;

    @Setter
    private String title;

    @Setter
    private String description;

    @Setter
    private LocalDate startDate;

    @Setter
    private LocalDate dueDate;

    @Builder
    public Card(Deck deck, User user, Long nextId, String title, String description,
        LocalDate startDate, LocalDate dueDate) {
        this.deck = deck;
        this.user = user;
        this.nextId = nextId;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.dueDate = dueDate;
    }
}