package com.sparta.trello.auth.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.trello.common.response.MessageResponseDto;
import com.sparta.trello.exception.custom.jwt.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@Component
public class JwtExceptionFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            filterChain.doFilter(request, response);
        } catch (JwtException e) {
            MessageResponseDto responseDto = new MessageResponseDto(
                e.getJwtCodeEnum().getHttpStatus().value(), e.getMessage()
            );
            String body = objectMapper.writeValueAsString(responseDto);

            response.setContentType("application/json;charset=UTF-8");
            response.setStatus(e.getJwtCodeEnum().getHttpStatus().value());
            response.getWriter().write(body);
        }
    }
}