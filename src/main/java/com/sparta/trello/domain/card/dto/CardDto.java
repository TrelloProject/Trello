package com.sparta.trello.domain.card.dto;

import com.sparta.trello.domain.card.entity.Card;
import java.time.LocalDate;
import lombok.Getter;

@Getter
public class CardDto {
    private Long id;
    private String title;
//    private String description;
//    private LocalDate startDate;
//    private LocalDate dueDate;

    public CardDto(Card card) {
        this.id = card.getId();
        this.title = card.getTitle();
//        this.description = card.getDescription();
//        this.startDate = card.getStartDate();
//        this.dueDate = card.getDueDate();
    }
}