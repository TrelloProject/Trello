package com.sparta.trello.exception.custom.boardMember.detail;

import com.sparta.trello.exception.custom.boardMember.BoardMemberException;

public class BoardMemberNotFoundException extends BoardMemberException {

    public BoardMemberNotFoundException(BoardMemberCodeEnum boardMemberCodeEnum) {
        super(boardMemberCodeEnum);
    }
}