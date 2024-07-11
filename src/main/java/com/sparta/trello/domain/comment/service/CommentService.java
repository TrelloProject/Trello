package com.sparta.trello.domain.comment.service;

import com.sparta.trello.domain.card.repository.CardAdapter;
import com.sparta.trello.domain.comment.dto.CommentResponseDto;
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

    @Transactional
    public CommentResponseDto createComment(CreateCommentRequestDto createCommentRequestDto,
        User user) {

        Comment comment = Comment.builder()
            .user(user)
            .card(cardAdapter.findById(createCommentRequestDto.getCardId()))
            .content(createCommentRequestDto.getContent())
            .build();
        Comment savedComment = commentAdapter.save(comment);
        return new CommentResponseDto(savedComment);
    }

    @Transactional
    public CommentResponseDto updateComment(Long commentId,
        UpdateCommentRequestDto updateCommentRequestDto, User user) {

        Comment comment = commentAdapter.findById(commentId);
        commentAdapter.validateCommentOwnership(comment, user);
        comment.setContent(updateCommentRequestDto.getContent());
        Comment updatedComment = commentAdapter.save(comment);
        return new CommentResponseDto(updatedComment);
    }

    @Transactional
    public void deleteComment(Long commentId, User user) {
        Comment comment = commentAdapter.findById(commentId);
        commentAdapter.validateCommentOwnership(comment, user);
        commentAdapter.delete(comment);
    }
}