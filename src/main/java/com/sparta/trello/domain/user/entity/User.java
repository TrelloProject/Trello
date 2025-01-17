package com.sparta.trello.domain.user.entity;

import com.sparta.trello.common.TimeStampEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends TimeStampEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true)
    private String username;

    private String password;

    private String refreshToken;

    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;

    @Enumerated(EnumType.STRING)
    private UserAuthRole authRole;

    @Builder
    public User(String name, String username, String password) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.userStatus = UserStatus.ACTIVATED;
        this.authRole = UserAuthRole.ROLE_USER;
    }

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void removeRefreshToken() {
        this.refreshToken = null;
    }

    public void withdrawUser() {
        this.userStatus = UserStatus.DEACTIVATED;
        this.refreshToken = null;
    }
}