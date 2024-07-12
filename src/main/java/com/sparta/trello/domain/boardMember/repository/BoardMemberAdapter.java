package com.sparta.trello.domain.boardMember.repository;

import com.sparta.trello.domain.board.entity.Board;
import com.sparta.trello.domain.boardMember.entity.BoardMember;
import com.sparta.trello.domain.boardMember.entity.BoardRole;
import com.sparta.trello.domain.user.entity.User;
import com.sparta.trello.exception.custom.boardMember.detail.BoardMemberCodeEnum;
import com.sparta.trello.exception.custom.boardMember.detail.BoardMemberDetailCustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class BoardMemberAdapter {
    private final BoardMemberRepository boardMemberRepository;
    
    public BoardMember save(BoardMember boardMember){
        return boardMemberRepository.save(boardMember);
    }

    public BoardMember findByBoardAndUser(Board board, User user){
        return boardMemberRepository.findByBoardAndUser(board, user)
                .orElseThrow(()-> new BoardMemberDetailCustomException(BoardMemberCodeEnum.BOARD_MEMBER_FORBIDDEN));
    }

    public BoardMember validateUserMember(Board board, User user){
        return boardMemberRepository.findByBoardAndUser(board, user).orElse(null);

    }

    public List<BoardMember> findByTwoBoardMember(List<Long> boardId) {
        return boardMemberRepository.findByTwoBoardMember(boardId);
    }
    
    public List<BoardMember> findByUser(User user){
        return boardMemberRepository.findByUser(user);
    }

    public void validateBoardMember(BoardMember member){
        if(member == null){
            throw new BoardMemberDetailCustomException(BoardMemberCodeEnum.BOARD_MEMBER_NOT_FOUND);
        }
        if(!member.getBoardRole().equals(BoardRole.MANAGER)) {
            throw new BoardMemberDetailCustomException(BoardMemberCodeEnum.BOARD_MEMBER_FORBIDDEN);
        }
    }
}