package com.sparta.trello.domain.board.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BoardDto {

    private Long id;
    private String title;
    private String description;
    private List<BoardItemsDto> boardItems;
}