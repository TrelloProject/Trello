package com.sparta.trello.auth.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.trello.common.response.MessageResponseDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    @Override
    public void commence(
        HttpServletRequest request, HttpServletResponse response, AuthenticationException authException
    ) throws IOException, ServletException {
        JwtCodeEnum jwtCodeEnum = (JwtCodeEnum) request.getAttribute("jwtException");
        int statusCode = jwtCodeEnum.getHttpStatus().value();
        String errorMessage = jwtCodeEnum.getMessage();

        MessageResponseDto responseDto = new MessageResponseDto(statusCode, errorMessage);
        String body = objectMapper.writeValueAsString(responseDto);

        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(statusCode);
        response.getWriter().write(body);
    }
}