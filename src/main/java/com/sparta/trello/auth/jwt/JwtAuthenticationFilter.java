package com.sparta.trello.auth.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.trello.auth.UserDetailsImpl;
import com.sparta.trello.common.response.MessageResponseDto;
import com.sparta.trello.domain.user.dto.LoginRequestDto;
import com.sparta.trello.domain.user.entity.User;
import com.sparta.trello.domain.user.entity.UserAuthRole;
import com.sparta.trello.domain.user.repository.UserAdapter;
import com.sparta.trello.domain.user.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
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
        setFilterProcessesUrl("/users/login");
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

        MessageResponseDto responseDto = new MessageResponseDto(HttpStatus.OK.value(), "로그인 성공");
        String body = objectMapper.writeValueAsString(responseDto);

        response.addHeader(JwtUtil.ACCESS_TOKEN_HEADER, accessToken);
        response.setStatus(HttpStatus.OK.value());
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(body);
    }

    @Override
    protected void unsuccessfulAuthentication(
        HttpServletRequest request, HttpServletResponse response, AuthenticationException failed
    ) throws IOException, ServletException {
        log.error("로그인 실패");
        MessageResponseDto responseDto = new MessageResponseDto(HttpStatus.UNAUTHORIZED.value(), "로그인 실패하였습니다.");
        String body = objectMapper.writeValueAsString(responseDto);

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(body);
    }
}