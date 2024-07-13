package com.sparta.trello.exception;

import com.sparta.trello.exception.custom.board.BoardException;
import com.sparta.trello.exception.custom.boardMember.BoardMemberException;
import com.sparta.trello.exception.custom.card.CardException;
import com.sparta.trello.exception.custom.comment.CommentException;
import com.sparta.trello.exception.custom.deck.DeckException;
import com.sparta.trello.exception.custom.exam.ExamException;
import com.sparta.trello.common.response.DataResponseDto;
import com.sparta.trello.common.response.MessageResponseDto;
import com.sparta.trello.common.response.ResponseUtils;
import com.sparta.trello.exception.custom.user.UserException;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionAdvice {

    @ExceptionHandler(CardException.class)
    public ResponseEntity<MessageResponseDto> handleCardException(CardException e) {
        log.error("에러 발생: ", e);
        return ResponseUtils.of(e.getCardCodeEnum().getHttpStatus(), e.getMessage());
    }

    @ExceptionHandler(CommentException.class)
    public ResponseEntity<MessageResponseDto> handleCommentException(CommentException e) {
        log.error("에러 발생: ", e);
        return ResponseUtils.of(e.getCommentCodeEnum().getHttpStatus(), e.getMessage());
    }

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
        log.error("예시 에러: ", e);
        return ResponseUtils.of(e.getDeckCodeEnum().getHttpStatus(), e.getMessage());
    }

    @ExceptionHandler(BoardException.class)
    public ResponseEntity<MessageResponseDto> handleUserException(BoardException e) {
        log.error("예시 에러: ", e);
        return ResponseUtils.of(e.getBoardCodeEnum().getHttpStatus(), e.getMessage());
    }

    @ExceptionHandler(BoardMemberException.class)
    public ResponseEntity<MessageResponseDto> handleUserException(BoardMemberException e) {
        log.error("예시 에러: ", e);
        return ResponseUtils.of(e.getBoardMemberCodeEnum().getHttpStatus(), e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<DataResponseDto<List<String>>> handleMethodArgumentNotValidException(
        MethodArgumentNotValidException e) {
        List<String> errorMessageList = new ArrayList<>();
        e.getBindingResult().getAllErrors()
            .forEach(v -> errorMessageList.add(v.getDefaultMessage()));
        log.error("유효성 검사 실패:\n\n{}", String.join(",\n", errorMessageList));
        return ResponseUtils.of(HttpStatus.BAD_REQUEST, "유효성 검사 실패", errorMessageList);
    }
}