package com.sparta.trello.auth;

import com.sparta.trello.domain.user.adapter.UserAdapter;
import com.sparta.trello.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserAdapter userAdapter;

    @Override
    public UserDetails loadUserByUsername(String username) {
        User findUser = userAdapter.findByUsername(username);
        userAdapter.checkWithdrawn(findUser);
        return new UserDetailsImpl(findUser);
    }
}