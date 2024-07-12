package com.sparta.trello.exception.custom.boardMember.detail;

import com.sparta.trello.exception.custom.boardMember.BoardMemberException;

public class BoardMemberDetailCustomException extends BoardMemberException {
    public BoardMemberDetailCustomException(BoardMemberCodeEnum boardMemberCodeEnum){
        super(boardMemberCodeEnum);
    }
}
