package com.sparta.trello.domain.exam.dto;

import com.sparta.trello.domain.exam.entity.Exam;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ExamResponseDto {
    private String title;
    private String content;

    public static ExamResponseDto of(Exam exam) {
        return ExamResponseDto.builder()
                .title(exam.getTitle())
                .content(exam.getContent())
                .build();
    }
}