package com.sparta.trello.domain.deck.entity;

import com.sparta.trello.common.TimeStampEntity;
import com.sparta.trello.domain.board.entity.Board;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "deck")
public class Deck extends TimeStampEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column
    private Long nextId;

    @Setter
    @Column
    private Long headCardId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    @Builder
    public Deck(String title, Long nextId, Board board) {
        this.title = title;
        this.nextId = nextId;
        this.board = board;
    }

    public void updateTitle(String title) {
        this.title = title;
    }

    public void updateNextId(Long nextId) {
        this.nextId = nextId;
    }
}