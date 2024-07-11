package com.sparta.trello.auth;

import com.sparta.trello.domain.user.entity.User;
import com.sparta.trello.domain.user.repository.UserAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

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