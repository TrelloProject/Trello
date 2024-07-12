package com.sparta.trello.domain.board.repository;

import com.sparta.trello.domain.board.entity.Board;
import com.sparta.trello.exception.custom.board.detail.BoardCodeEnum;
import com.sparta.trello.exception.custom.board.detail.BoardDetailCustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class BoardAdapter {
    private final BoardRepository boardRepository;

    public Board save(Board board){
        return boardRepository.save(board);
    }

    public void delete(Board board){
        boardRepository.delete(board);
    }

    public Board findById(Long id){
        return boardRepository.findById(id).orElseThrow(() ->
                new BoardDetailCustomException(BoardCodeEnum.BOARD_NOT_FOUND));
    }
}
