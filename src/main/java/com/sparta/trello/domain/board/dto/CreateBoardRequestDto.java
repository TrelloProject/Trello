package com.sparta.trello.domain.board.dto;

import lombok.Getter;

@Getter
public class CreateBoardRequestDto {

    private String title;
    private String description;
}