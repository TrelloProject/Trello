package com.sparta.trello.domain.boardMember.repository;

import com.sparta.trello.domain.board.entity.Board;
import com.sparta.trello.domain.boardMember.entity.BoardMember;
import com.sparta.trello.domain.user.entity.User;
import com.sparta.trello.exception.custom.exam.ExamCodeEnum;
import com.sparta.trello.exception.custom.exam.ExamException;
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
                .orElseThrow(()->new ExamException(ExamCodeEnum.EXAM_NOT_FOUND));
    }
    
    public List<BoardMember> findByUser(User user){
        return boardMemberRepository.findByUser(user);
    }
}
