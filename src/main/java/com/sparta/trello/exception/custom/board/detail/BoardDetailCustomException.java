package com.sparta.trello.exception.custom.board.detail;

import com.sparta.trello.exception.custom.board.BoardException;

public class BoardDetailCustomException extends BoardException {
    public BoardDetailCustomException(BoardCodeEnum boardCodeEnum){
        super(boardCodeEnum);
    }
}
