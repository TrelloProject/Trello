package com.sparta.trello.domain.comment.service;

import com.sparta.trello.domain.board.entity.Board;
import com.sparta.trello.domain.boardMember.entity.BoardMember;
import com.sparta.trello.domain.boardMember.repository.BoardMemberAdapter;
import com.sparta.trello.domain.card.adapter.CardAdapter;
import com.sparta.trello.domain.card.entity.Card;
import com.sparta.trello.domain.comment.dto.CreateCommentRequestDto;
import com.sparta.trello.domain.comment.dto.UpdateCommentRequestDto;
import com.sparta.trello.domain.comment.entity.Comment;
import com.sparta.trello.domain.comment.repository.CommentAdapter;
import com.sparta.trello.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class CommentService {

    private final CardAdapter cardAdapter;
    private final CommentAdapter commentAdapter;
    private final BoardMemberAdapter boardMemberAdapter;

    @Transactional
    public void createComment(CreateCommentRequestDto createCommentRequestDto,
        User user) {

        // 보드 멤버 검증
        Card card = cardAdapter.findById(createCommentRequestDto.getCardId());
        Board board = card.getDeck().getBoard();
        BoardMember boardMember = boardMemberAdapter.findByBoardAndUser(board, user);
        boardMemberAdapter.validateBoardMember(boardMember);

        Comment comment = Comment.builder()
            .user(user)
            .card(cardAdapter.findById(createCommentRequestDto.getCardId()))
            .content(createCommentRequestDto.getContent())
            .build();
        commentAdapter.save(comment);
    }

    @Transactional
    public void updateComment(Long commentId,
        UpdateCommentRequestDto updateCommentRequestDto, User user) {

        Comment comment = commentAdapter.findById(commentId);
        commentAdapter.validateCommentOwnership(comment, user);

        // 보드 멤버 검증
        Board board = comment.getCard().getDeck().getBoard();
        BoardMember boardMember = boardMemberAdapter.findByBoardAndUser(board, user);
        boardMemberAdapter.validateBoardMember(boardMember);

        comment.setContent(updateCommentRequestDto.getContent());
        commentAdapter.save(comment);
    }

    @Transactional
    public void deleteComment(Long commentId, User user) {
        Comment comment = commentAdapter.findById(commentId);
        commentAdapter.validateCommentOwnership(comment, user);

        // 보드 멤버 검증
        Board board = comment.getCard().getDeck().getBoard();
        BoardMember boardMember = boardMemberAdapter.findByBoardAndUser(board, user);
        boardMemberAdapter.validateBoardMember(boardMember);
        
        commentAdapter.delete(comment);
    }
}