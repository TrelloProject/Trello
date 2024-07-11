package com.sparta.trello.domain.comment.dto;

import com.sparta.trello.domain.card.entity.Card;
import com.sparta.trello.domain.comment.entity.Comment;
import com.sparta.trello.domain.user.entity.User;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class CommentResponseDto {

    private final Long commentId;

    private final User user;

    private final Card card;

    private final String content;

    private final LocalDateTime createdAt;

    private final LocalDateTime updatedAt;

    public CommentResponseDto(Comment comment) {
        this.commentId = comment.getId();
        this.user = comment.getUser();
        this.card = comment.getCard();
        this.content = comment.getContent();
        this.createdAt = comment.getCreatedAt();
        this.updatedAt = comment.getUpdatedAt();
    }
}