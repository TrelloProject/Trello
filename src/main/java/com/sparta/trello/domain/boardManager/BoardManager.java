package com.sparta.trello.domain.boardManager;

import com.sparta.trello.common.TimeStampEntity;
import com.sparta.trello.domain.exam.entity.Board;
import com.sparta.trello.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "boardManger")
public class BoardManager extends TimeStampEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private BoardRole boardRole;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    @Builder
    public BoardManager(BoardRole boardRole, User user, Board board) {
        this.boardRole = boardRole;
        this.user = user;
        this.board = board;
    }
}
