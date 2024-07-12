package com.sparta.trello.domain.board.dto;

import lombok.Getter;

@Getter
public class CreateBoardRequest {
    private String title;

    private String description;
}
