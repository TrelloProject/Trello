package com.sparta.trello.domain.board.entity;

import com.sparta.trello.common.TimeStampEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "board")
public class Board extends TimeStampEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column
    private Long headDeckId;

    @Column
    private String description;

    public void updateHeadDeckId(Long headDeckId){
        this.headDeckId = headDeckId;
    }
    @Builder
    public Board(String title,String description) {
        this.title = title;
        this.description = description;
    }
    //수정용
    public void update(String title,String description) {
        this.title = title;
        this.description = description;
    }
}