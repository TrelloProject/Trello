package com.sparta.trello.domain.comment.entity;

import com.sparta.trello.common.TimeStampEntity;
import com.sparta.trello.domain.card.entity.Card;
import com.sparta.trello.domain.user.entity.User;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "comment")
public class Comment extends TimeStampEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OnDelete(action = OnDeleteAction.CASCADE) //삭제를 테스트 위해 임시로 적어둠
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card_id", nullable = false)
    private Card card;

    private String content;
}