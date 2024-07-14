package com.sparta.trello.auth.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.trello.auth.UserDetailsImpl;
import com.sparta.trello.common.response.MessageResponseDto;
import com.sparta.trello.domain.user.adapter.UserAdapter;
import com.sparta.trello.domain.user.dto.LoginRequestDto;
import com.sparta.trello.domain.user.entity.User;
import com.sparta.trello.domain.user.entity.UserAuthRole;
import com.sparta.trello.exception.custom.user.detail.UserWithdrawnException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final JwtUtil jwtUtil;
    private final ObjectMapper objectMapper;
    private final UserAdapter userAdapter;

    public JwtAuthenticationFilter(JwtUtil jwtUtil, ObjectMapper objectMapper, UserAdapter userAdapter) {
        this.jwtUtil = jwtUtil;
        this.objectMapper = objectMapper;
        this.userAdapter = userAdapter;
        setFilterProcessesUrl("/api/users/login");
    }

    @Override
    public Authentication attemptAuthentication(
        HttpServletRequest request, HttpServletResponse response
    ) throws AuthenticationException {
        try {
            LoginRequestDto requestDto = new ObjectMapper().readValue(request.getInputStream(), LoginRequestDto.class);

            return getAuthenticationManager().authenticate(
                new UsernamePasswordAuthenticationToken(
                    requestDto.getUsername(),
                    requestDto.getPassword(),
                    null
                )
            );
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    protected void successfulAuthentication(
        HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult
    ) throws IOException, ServletException {
        String username = ((UserDetailsImpl) authResult.getPrincipal()).getUsername();
        UserAuthRole role = ((UserDetailsImpl) authResult.getPrincipal()).getUser().getAuthRole();
        User findUser = ((UserDetailsImpl) authResult.getPrincipal()).getUser();

        String accessToken = jwtUtil.createAccessToken(username, role);
        String refreshToken = jwtUtil.createRefreshToken(username, role);
        findUser.updateRefreshToken(refreshToken);
        userAdapter.save(findUser);

        Cookie cookie = new Cookie(JwtUtil.ACCESS_TOKEN_HEADER, accessToken.replace(" ", "%20"));
        cookie.setPath("/");
        cookie.setMaxAge((int) JwtUtil.ACCESS_TOKEN_TIME);
        response.addCookie(cookie);
        sendResponse(response, HttpStatus.OK, "로그인 성공");
    }

    @Override
    protected void unsuccessfulAuthentication(
        HttpServletRequest request, HttpServletResponse response, AuthenticationException failed
    ) throws IOException, ServletException {
        log.error("로그인 실패");
        if (failed.getCause() instanceof UserWithdrawnException e) {
            sendResponse(response, e.getUserCodeEnum().getHttpStatus(), e.getMessage());
        } else {
            sendResponse(response, HttpStatus.UNAUTHORIZED, "로그인 실패하였습니다.");
        }
    }

    private void sendResponse(HttpServletResponse response, HttpStatus status, String message) throws IOException {
        MessageResponseDto responseDto = new MessageResponseDto(status.value(), message);
        String body = objectMapper.writeValueAsString(responseDto);

        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(status.value());
        response.getWriter().write(body);
    }


}