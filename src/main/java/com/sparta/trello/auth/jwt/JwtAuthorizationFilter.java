package com.sparta.trello.auth.jwt;

import com.sparta.trello.auth.UserDetailsServiceImpl;
import com.sparta.trello.domain.user.entity.User;
import com.sparta.trello.domain.user.repository.UserAdapter;
import com.sparta.trello.exception.custom.jwt.JwtCodeEnum;
import com.sparta.trello.exception.custom.jwt.detail.JwtAlreadyRemoveException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;
    private final UserAdapter userAdapter;

    private final List<String> getMethodWhiteList = List.of(
    );

    private final List<String> anyMethodWhiteList = List.of(
        "/users/login", "/users/signup"
    );

    @Override
    protected void doFilterInternal(
        HttpServletRequest request, HttpServletResponse response, FilterChain filterChain
    ) throws ServletException, IOException {
        String uri = request.getRequestURI();
        String method = request.getMethod();

        // 어떤 요청이든 인증을 요구하지 않음
        if (anyMethodWhiteList.stream().anyMatch(uri::startsWith)) {
            filterChain.doFilter(request, response);
            return;
        }
        // GET 요청에 대해서는 인증을 요구하지 않음
        if (HttpMethod.GET.matches(method) && getMethodWhiteList.stream().anyMatch(uri::startsWith)) {
            filterChain.doFilter(request, response);
            return;
        }

        String accessToken = jwtUtil.getAccessTokenFromHeader(request);
        if (StringUtils.hasText(accessToken) && jwtUtil.validateToken(accessToken)) {
            String username = jwtUtil.getUserInfoFromToken(accessToken).getSubject();
            User findUser = userAdapter.findByUsername(username);

            if (findUser.getRefreshToken() != null) {
                String refreshToken = findUser.getRefreshToken().substring(JwtUtil.BEARER_PREFIX.length());
                if (StringUtils.hasText(refreshToken) && jwtUtil.validateToken(refreshToken)) {
                    log.info("Context Holder 에 담기");
                    setAuthentication(findUser.getUsername());
                }
            } else {
                log.error("DB에 refresh 토큰 없음. 로그인부터 해야 됨");
                throw new JwtAlreadyRemoveException(JwtCodeEnum.JWT_NOT_FOUND);
            }
        } else {
            log.error("access 토큰 검증 실패, refresh 토큰 검증 시작");

            if (StringUtils.hasText(accessToken)) {
                String username = jwtUtil.getUserInfoFromToken(accessToken).getSubject();
                User findUser = userAdapter.findByUsername(username);
                String refreshToken = findUser.getRefreshToken();

                if (StringUtils.hasText(refreshToken) && jwtUtil.validateToken(refreshToken)) {
                    String newToken = jwtUtil.createAccessToken(username, findUser.getAuthRole());
                    response.setHeader(JwtUtil.ACCESS_TOKEN_HEADER, newToken);
                    setAuthentication(username);
                }
            } else {
                log.error("access 토큰 없음. 로그인부터 해야 됨");
                throw new JwtAlreadyRemoveException(JwtCodeEnum.JWT_NOT_FOUND);
            }
        }

        filterChain.doFilter(request, response);
    }

    // 인증 처리
    private void setAuthentication(String username) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication authentication = createAuthentication(username);
        context.setAuthentication(authentication);

        SecurityContextHolder.setContext(context);
    }

    // 인증 객체 생성
    private Authentication createAuthentication(String username) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}