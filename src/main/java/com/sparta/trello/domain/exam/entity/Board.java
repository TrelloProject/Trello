package com.sparta.trello.domain.exam.entity;

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
    private String headColumnId;

    @Column
    private String description;

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