package com.sparta.trello.exception.custom.exam.detail;

import com.sparta.trello.exception.custom.exam.ExamCodeEnum;
import com.sparta.trello.exception.custom.exam.ExamException;

public class ExamDetailCustomException extends ExamException {
    public ExamDetailCustomException(ExamCodeEnum examCodeEnum) {
        super(examCodeEnum);
    }
}