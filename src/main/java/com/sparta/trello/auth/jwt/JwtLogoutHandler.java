package com.sparta.trello.auth.jwt;

import com.sparta.trello.domain.user.entity.User;
import com.sparta.trello.domain.user.adapter.UserAdapter;
import com.sparta.trello.exception.custom.jwt.JwtCodeEnum;
import com.sparta.trello.exception.custom.jwt.JwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtLogoutHandler implements LogoutHandler {

    private final JwtUtil jwtUtil;
    private final UserAdapter userAdapter;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        log.info("로그아웃 시작");
        String accessToken = jwtUtil.getAccessTokenFromHeader(request);
        if (accessToken == null) {
            throw new JwtException(JwtCodeEnum.JWT_NOT_FOUND);
        }

        String username = jwtUtil.getUserInfoFromToken(accessToken).getSubject();
        User finduser = userAdapter.findByUsername(username);

        if (finduser.getRefreshToken() == null) {
            throw new JwtException(JwtCodeEnum.JWT_NOT_FOUND);
        }
        finduser.removeRefreshToken();
        userAdapter.save(finduser);

        SecurityContextHolder.clearContext();
    }
}