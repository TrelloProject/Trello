package com.sparta.trello.domain.user.service;

import com.sparta.trello.domain.user.dto.SignupRequestDto;
import com.sparta.trello.domain.user.entity.User;
import com.sparta.trello.domain.user.repository.UserAdapter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserAdapter userAdapter;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void signup(SignupRequestDto requestDto) {
        userAdapter.validUserSignup(requestDto.getUsername());

        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());
        User savedUser = userAdapter.save(User.builder()
            .name(requestDto.getName())
            .username(requestDto.getUsername())
            .password(encodedPassword)
            .build());
    }

    public void withdraw(User loginUser) {
        loginUser.withdrawUser();
        userAdapter.save(loginUser);
    }
}