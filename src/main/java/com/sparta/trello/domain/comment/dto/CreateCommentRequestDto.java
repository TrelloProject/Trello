package com.sparta.trello.domain.comment.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateCommentRequestDto {

    @NotBlank(message = "카드 ID를 입력해야 합니다.")
    private Long cardId;

    @NotBlank(message = "댓글 내용을 입력해야 합니다.")
    private String content;
}