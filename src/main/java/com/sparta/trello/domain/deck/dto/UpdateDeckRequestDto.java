package com.sparta.trello.domain.deck.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UpdateDeckRequestDto {

    @NotBlank(message = "제목 입력하세요.")
    private String title;
}
