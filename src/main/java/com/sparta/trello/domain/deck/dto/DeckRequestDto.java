package com.sparta.trello.domain.deck.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class DeckRequestDto {

    @NotNull(message = "보드 id를 입력하세요.")
    private Long boardId;

    @NotBlank(message = "제목 입력하세요.")
    private String title;
}
