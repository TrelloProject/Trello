package com.sparta.trello.domain.card.dto;

import com.sparta.trello.domain.card.entity.Card;
import com.sparta.trello.domain.comment.dto.CommentResponseDto;
import com.sparta.trello.domain.comment.entity.Comment;
import java.time.LocalDate;
import java.util.List;
import lombok.Getter;

@Getter
public class CardResponseDto {

    private Long id;
    private String title;
    private String description;
    private LocalDate startDate;
    private LocalDate dueDate;
    private List<CommentResponseDto> comments;

    public CardResponseDto(Card card, List<Comment> comments) {
        this.id = card.getId();
        this.title = card.getTitle();
        this.description = card.getDescription();
        this.startDate = card.getStartDate();
        this.dueDate = card.getDueDate();
        this.comments = comments.stream().map(CommentResponseDto::new).toList();
    }
}