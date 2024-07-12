package com.sparta.trello.exception.custom.board;

import com.sparta.trello.exception.custom.board.detail.BoardCodeEnum;
import lombok.Getter;

@Getter
public class BoardException extends RuntimeException{
    private final BoardCodeEnum boardCodeEnum;

    public BoardException(BoardCodeEnum boardCodeEnum){
        super(boardCodeEnum.getMessage());
        this.boardCodeEnum = boardCodeEnum;
    }
}
