package com.sparta.trello.domain.board.repository;

import com.sparta.trello.domain.board.entity.Board;
import com.sparta.trello.domain.boardMember.entity.BoardMember;
import com.sparta.trello.domain.boardMember.entity.BoardRole;
import com.sparta.trello.exception.custom.board.detail.BoardCodeEnum;
import com.sparta.trello.exception.custom.board.detail.BoardDetailCustomException;
import com.sparta.trello.exception.custom.boardMember.detail.BoardMemberCodeEnum;
import com.sparta.trello.exception.custom.boardMember.detail.BoardMemberDetailCustomException;
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

    public void validateBoardMember(BoardMember member){
        if(member == null){
            throw new BoardMemberDetailCustomException(BoardMemberCodeEnum.BOARD_MEMBER_NOT_FOUND);
        }
        if(member.getBoardRole() != BoardRole.MANAGER) {
            throw new BoardMemberDetailCustomException(BoardMemberCodeEnum.BOARD_MEMBER_FORBIDDEN);
        }
    }
}
