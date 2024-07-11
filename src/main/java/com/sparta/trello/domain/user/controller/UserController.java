package com.sparta.trello.domain.user.controller;

import com.sparta.trello.auth.UserDetailsImpl;
import com.sparta.trello.common.response.MessageResponseDto;
import com.sparta.trello.common.response.ResponseUtils;
import com.sparta.trello.domain.user.dto.SignupRequestDto;
import com.sparta.trello.domain.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<MessageResponseDto> signup(
        @Valid @RequestBody SignupRequestDto requestDto
    ) {
        userService.signup(requestDto);
        return ResponseUtils.of(HttpStatus.CREATED, "회원가입 성공");
    }

    @DeleteMapping("/withdraw")
    public ResponseEntity<MessageResponseDto> withdraw(
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        userService.withdraw(userDetails.getUser());
        return ResponseUtils.of(HttpStatus.OK, "회원탈퇴 성공");
    }

    // @AuthenticationPrincipal UserDetailsImpl userDetails
}