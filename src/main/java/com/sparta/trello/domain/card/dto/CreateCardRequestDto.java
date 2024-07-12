package com.sparta.trello.domain.card.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateCardRequestDto {

    @NotNull(message = "덱 ID를 입력해 주세요.")
    private Long deckId;

    @NotBlank(message = "카드 제목을 입력해 주세요.")
    private String title;

    @NotBlank(message = "카드 설명을 입력해 주세요.")
    private String description;
}