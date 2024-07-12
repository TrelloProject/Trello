package com.sparta.trello.domain.board.dto;

import com.sparta.trello.domain.board.entity.Board;
import lombok.Getter;

@Getter
public class BoardResponseDto {

    private final String title;
    private final String description;

    public BoardResponseDto(Board board) {
        this.title = board.getTitle();
        this.description = board.getDescription();
    }
}
