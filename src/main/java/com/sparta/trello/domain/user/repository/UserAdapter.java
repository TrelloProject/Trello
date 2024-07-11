package com.sparta.trello.domain.user.repository;

import com.sparta.trello.domain.user.entity.User;
import com.sparta.trello.exception.custom.user.UserCodeEnum;
import com.sparta.trello.exception.custom.user.detail.DuplicatedUsernameUserException;
import com.sparta.trello.exception.custom.user.detail.UserNotFoundException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserAdapter {

    private final UserRepository userRepository;

    public User save(User user) {
        return userRepository.save(user);
    }

    public void validUserSignup(String username) {
        Optional<User> findUser = userRepository.findByUsername(username);
        if (findUser.isPresent()) {
            throw new DuplicatedUsernameUserException(UserCodeEnum.DUPLICATED_USERNAME);
        }
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
            .orElseThrow(() -> new UserNotFoundException(UserCodeEnum.NOT_FOUND));
    }
}