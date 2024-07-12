package com.sparta.trello.exception.custom.boardMember;

import com.sparta.trello.exception.custom.boardMember.detail.BoardMemberCodeEnum;
import lombok.Getter;

@Getter
public class BoardMemberException extends RuntimeException{
    private final BoardMemberCodeEnum boardMemberCodeEnum;

    public BoardMemberException(BoardMemberCodeEnum boardMemberCodeEnum){
        super(boardMemberCodeEnum.getMessage());
        this.boardMemberCodeEnum = boardMemberCodeEnum;
    }
}
