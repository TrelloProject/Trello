package com.sparta.trello.exception;

import com.sparta.trello.exception.custom.deck.DeckException;
import com.sparta.trello.exception.custom.exam.ExamException;
import com.sparta.trello.common.response.DataResponseDto;
import com.sparta.trello.common.response.MessageResponseDto;
import com.sparta.trello.common.response.ResponseUtils;
import com.sparta.trello.exception.custom.user.UserException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionAdvice {

    // 예시
    @ExceptionHandler(ExamException.class)
    public ResponseEntity<MessageResponseDto> handleExamException(ExamException e) {
        log.error("예시 에러: ", e);
        return ResponseUtils.of(e.getExamCodeEnum().getHttpStatus(), e.getMessage());
    }

    @ExceptionHandler(UserException.class)
    public ResponseEntity<MessageResponseDto> handleUserException(UserException e) {
        log.error("예시 에러: ", e);
        return ResponseUtils.of(e.getUserCodeEnum().getHttpStatus(), e.getMessage());
    }

    @ExceptionHandler(DeckException.class)
    public ResponseEntity<MessageResponseDto> handleUserException(DeckException e) {
        return ResponseUtils.of(e.getDeckCodeEnum().getHttpStatus(), e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<DataResponseDto<List<String>>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<String> errorMessageList = new ArrayList<>();
        e.getBindingResult().getAllErrors().forEach(v -> errorMessageList.add(v.getDefaultMessage()));
        log.error("유효성 검사 실패:\n\n{}", String.join(",\n", errorMessageList));
        return ResponseUtils.of(HttpStatus.BAD_REQUEST, "유효성 검사 실패", errorMessageList);
    }
}