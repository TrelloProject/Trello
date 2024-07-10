package com.sparta.trello.domain.exam.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class ExamCreateRequestDto {
    @NotBlank(message = "제목 입력하세요.")
    private String title;
    @NotBlank(message = "내용 입력하세요.")
    private String content;
}