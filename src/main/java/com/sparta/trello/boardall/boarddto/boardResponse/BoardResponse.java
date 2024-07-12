package com.sparta.trello.boardall.boarddto.boardResponse;

import com.sparta.trello.domain.exam.entity.Board;
import lombok.Getter;

@Getter
public class BoardResponse {

    private final String title;
    private final String description;

    public BoardResponse(Board board) {
        this.title = board.getTitle();
        this.description = board.getDescription();
    }
}
