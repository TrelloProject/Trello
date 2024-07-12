package com.sparta.trello.domain.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class GrantBoardRoleDto {

    @NotBlank(message = "권한을 줄 사용자 아이디를 입력하세요.")
    private String username;
    @NotNull(message = "권한을 줄 보드 ID값을 입력하세요.")
    private Long boardId;
}