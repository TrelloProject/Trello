package com.sparta.trello.domain.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class SignupRequestDto {

    @NotBlank(message = "아이디를 입력하세요.")
    @Size(min = 4, max = 12, message = "아이디의 길이는 4~12자 입니다.")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "영어 대소문자와 숫자만 입력 가능합니다.")
    private String username;

    @NotBlank(message = "비밀번호를 입력하세요.")
    @Size(min = 4, max = 12)
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]+$", message = "영어 대소문자, 숫자, 특수문자를 각각 1개 이상 포함해야 합니다.")
    private String password;

    @NotBlank(message = "이름을 입력하세요.")
    @Size(min = 4, max = 12)
    @Pattern(regexp = "^[a-zA-Z가-힣]+$", message = "영어 대소문자와 한글만 입력 가능합니다.")
    private String name;
}