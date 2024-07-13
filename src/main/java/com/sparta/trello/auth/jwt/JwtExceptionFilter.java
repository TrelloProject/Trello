package com.sparta.trello.auth.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.trello.common.response.MessageResponseDto;
import com.sparta.trello.exception.custom.jwt.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtExceptionFilter extends OncePerRequestFilter {

    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (JwtException e) {
            MessageResponseDto responseDto = new MessageResponseDto(
                e.getJwtCodeEnum().getHttpStatus().value(), e.getMessage()
            );
            responseError(response, e.getJwtCodeEnum().getHttpStatus().value(), responseDto);
        }
    }

    private void responseError(HttpServletResponse response, int statusCode, MessageResponseDto responseDto) throws IOException {
        String body = objectMapper.writeValueAsString(responseDto);

        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(statusCode);
        response.getWriter().write(body);
    }
}