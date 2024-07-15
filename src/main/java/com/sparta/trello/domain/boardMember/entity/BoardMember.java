package com.sparta.trello.domain.boardMember.entity;

import com.sparta.trello.common.TimeStampEntity;
import com.sparta.trello.domain.board.entity.Board;
import com.sparta.trello.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "board_member",
        indexes = {
                @Index(name = "idx_user_id", columnList = "user_id"),
                @Index(name = "idx_board_id", columnList = "board_id")
        })
public class BoardMember extends TimeStampEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private BoardRole boardRole;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    @Builder
    public BoardMember(BoardRole boardRole, User user, Board board) {
        this.boardRole = boardRole;
        this.user = user;
        this.board = board;
    }

    public void grantBoardManager() {
        this.boardRole = BoardRole.MANAGER;
    }
}