package com.sparta.trello.auth.jwt;

import com.sparta.trello.domain.user.entity.UserAuthRole;
import com.sparta.trello.exception.custom.jwt.JwtCodeEnum;
import com.sparta.trello.exception.custom.jwt.JwtException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Slf4j
@Component
public class JwtUtil {

    // Header KEY 값
    public static final String ACCESS_TOKEN_HEADER = "Authorization";
    public static final String REFRESH_TOKEN_HEADER = "Authorization-refresh";
    // Token 식별자
    public static final String BEARER_PREFIX = "Bearer ";
    // 토큰 만료시간
    public static final long ACCESS_TOKEN_TIME = 30 * 60 * 1000L; // 30분
    public static final long REFRESH_TOKEN_TIME = 1000 * 60 * 60 * 24 * 14; // 2주

    @Value("${jwt-secret-key}") // Base64 Encode 한 SecretKey
    private String secretKey;
    private Key key;
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    @PostConstruct
    public void init() {
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes);
    }

    // access token 생성
    public String createAccessToken(String username, UserAuthRole role) {
        Date date = new Date();

        return BEARER_PREFIX +
            Jwts.builder()
                .setId(UUID.randomUUID().toString())
                .setSubject(username)
                .setIssuedAt(new Date(date.getTime()))
                .setExpiration(new Date(date.getTime() + ACCESS_TOKEN_TIME))
                .claim("tokenType", "access")
                .signWith(key, signatureAlgorithm)
                .compact();
    }

    // refresh token 생성
    public String createRefreshToken(String username, UserAuthRole role) {
        Date date = new Date();

        return BEARER_PREFIX +
            Jwts.builder()
                .setId(UUID.randomUUID().toString())
                .setSubject(username)
                .setIssuedAt(new Date(date.getTime()))
                .setExpiration(new Date(date.getTime() + REFRESH_TOKEN_TIME))
                .claim("tokenType", "refresh")
                .signWith(key, signatureAlgorithm)
                .compact();
    }

    // header 에서 access token 가져오기
    public String getAccessTokenFromHeader(HttpServletRequest request) {
        String bearerToken = request.getHeader(ACCESS_TOKEN_HEADER);
        log.info("Bearer token: {}", bearerToken);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            String replace = bearerToken.replace("%20", " ");
            return replace.substring(BEARER_PREFIX.length());
        }
        return null;
    }

    // 쿠키에서 access token 가져오기
    public String getAccessTokenFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (ACCESS_TOKEN_HEADER.equals(cookie.getName())) {
                    String replace = cookie.getValue().replace("%20", " ");
                    if (replace.startsWith(BEARER_PREFIX)) {
                        replace = replace.substring(BEARER_PREFIX.length());
                    }
                    return URLDecoder.decode(replace, StandardCharsets.UTF_8);
                }
            }
        }
        return null;
    }

    // 토큰 검증
    public boolean validateToken(String token) {
        token = token.replace("%20", " ");
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException | SignatureException e) {
            log.error("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.");
            throw new JwtException(JwtCodeEnum.INVALID_JWT_SIGNATURE);
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT token, 만료된 JWT token 입니다.");
            return false;
//            throw new JwtException(JwtCodeEnum.EXPIRED_JWT_TOKEN);
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.");
            throw new JwtException(JwtCodeEnum.UNSUPPORTED_JWT_TOKEN);
        } catch (IllegalArgumentException e) {
            log.error("JWT claims is empty, 잘못된 JWT 토큰 입니다.");
            throw new JwtException(JwtCodeEnum.EMPTY_JWT_CLAIMS);
        }
    }

    // 토큰에서 사용자 정보 가져오기
    public Claims getUserInfoFromToken(String token) {
        if (validateToken(token)) {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        }
        return null;
    }
}