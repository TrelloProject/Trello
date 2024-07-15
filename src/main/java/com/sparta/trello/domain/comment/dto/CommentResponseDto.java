package com.sparta.trello.domain.comment.dto;

import com.sparta.trello.domain.card.entity.Card;
import com.sparta.trello.domain.comment.entity.Comment;
import com.sparta.trello.domain.user.entity.User;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDto {

    private final Long id;
    private final String content;

//    private final User user;
//    private final Card card;
//    private final LocalDateTime createdAt;
//    private final LocalDateTime updatedAt;

    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
//        this.user = comment.getUser();
//        this.card = comment.getCard();
//        this.createdAt = comment.getCreatedAt();
//        this.updatedAt = comment.getUpdatedAt();
    }
}