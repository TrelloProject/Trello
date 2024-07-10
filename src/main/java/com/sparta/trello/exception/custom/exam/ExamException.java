package com.sparta.trello.exception.custom.exam;

import lombok.Getter;

@Getter
public class ExamException extends RuntimeException {

    private final ExamCodeEnum examCodeEnum;

    public ExamException(ExamCodeEnum examCodeEnum) {
        super(examCodeEnum.getMessage());
        this.examCodeEnum = examCodeEnum;
    }
}