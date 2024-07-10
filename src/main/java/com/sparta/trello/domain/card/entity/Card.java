package com.sparta.trello.domain.card.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

@Getter
@Entity
@Table(name = "cards")
public class Card {

    @Id
    private Long id;
}