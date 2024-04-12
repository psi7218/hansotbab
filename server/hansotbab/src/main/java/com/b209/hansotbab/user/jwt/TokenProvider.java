package com.b209.hansotbab.user.jwt;

import com.b209.hansotbab.user.entity.User;
import com.b209.hansotbab.user.entity.UserPrincipal;
import com.b209.hansotbab.user.exception.NoUserExistsException;
import com.b209.hansotbab.user.exception.NotAuthenticactedException;
import com.b209.hansotbab.user.repository.UserRepository;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.*;

@Slf4j
@Component
public class TokenProvider {

    public static final String ACCESS_TOKEN_NAME = "access_token";
    public static final String REFRESH_TOKEN_NAME = "refresh_token";
    public static final long ACCESS_TOKEN_EXPIRE_TIME_IN_MILLISECONDS = 1000L * 60L * 120L; // 30min
    public static final long REFRESH_TOKEN_EXPIRE_TIME_IN_MILLISECONDS = 1000L * 60L * 60L * 24L * 14L; // 2weeks
    public static final int FLUTTER_TOKEN_EXPIRE_TIME_IN_SECONDS = 60 * 60 * 1000 * 24 * 21;
    public static final String ISSUER = "Hansotbab";

    @Value("${jwt.secret.key}")
    private String secretKey;
    private Key keyStream;
    private Date date;

    private final UserRepository userRepository;

    public TokenProvider(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostConstruct
    public void init() {
        byte[] key = Decoders.BASE64URL.decode(secretKey);
        this.keyStream = Keys.hmacShaKeyFor(key);
        this.date = new Date();
    }

    public boolean isValidToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(keyStream)
                    .build().parseClaimsJws(token);
            return true;

        } catch (ExpiredJwtException e) {
            log.error("Expired Token");
        } catch (UnsupportedJwtException | MalformedJwtException e) {
            log.error("Unsupported Token Type");
        } catch (IllegalArgumentException e) {
            log.error("Null or empty token");
        } catch (SignatureException e) {
            log.error("Invalid Token Signature");
        }
        return false;
    }

    public String createFlutterToken(UserPrincipal principal) {
        Date rtkExpiryDate = new Date(date.getTime() + FLUTTER_TOKEN_EXPIRE_TIME_IN_SECONDS);

        if(principal.getEmail().isEmpty()) {
            throw new NotAuthenticactedException();
        }

        return Jwts.builder()
                .claim("type", ACCESS_TOKEN_NAME)
                .setIssuer(ISSUER)
                .setSubject(principal.getEmail())
                .setIssuedAt(date)
                .setExpiration(rtkExpiryDate)
                .setNotBefore(date)
                .signWith(keyStream, SignatureAlgorithm.HS256)
                .compact();
    }

    public String createAccessToken(UserPrincipal principal) {
        return getJwt(principal, ACCESS_TOKEN_EXPIRE_TIME_IN_MILLISECONDS, ACCESS_TOKEN_NAME);
    }

    public String createRefreshToken(UserPrincipal principal) {
        return getJwt(principal, REFRESH_TOKEN_EXPIRE_TIME_IN_MILLISECONDS, REFRESH_TOKEN_NAME);
    }

    private String getJwt(UserPrincipal principal, long tokenExpireTimeInMilliseconds, String tokenName) {
        Date rtkExpiryDate = new Date(date.getTime() + tokenExpireTimeInMilliseconds);

        if(principal.getEmail().isEmpty()) {
            throw new NotAuthenticactedException();
        }

        return Jwts.builder()
                .claim("type", tokenName)
                .setIssuer(ISSUER)
                .setSubject(principal.getEmail())
                .setIssuedAt(date)
                .setExpiration(rtkExpiryDate)
                .setNotBefore(date)
                .signWith(keyStream, SignatureAlgorithm.HS256)
                .compact();
    }

    public String createRefreshToken(String email) {
        Date rtkExpiryDate = new Date(date.getTime() + REFRESH_TOKEN_EXPIRE_TIME_IN_MILLISECONDS);

        return Jwts.builder()
                .claim("type", REFRESH_TOKEN_NAME)
                .setIssuer(ISSUER)
                .setSubject(email)
                .setIssuedAt(date)
                .setExpiration(rtkExpiryDate)
                .setNotBefore(date)
                .signWith(keyStream, SignatureAlgorithm.HS256)
                .compact();
    }

    // API 호출 시 HTTP Request에 포함된 Access Token으로 유저 정보 조회 & Authentication 객체 생성
    public Authentication getAuthentication(String accessToken) {
        Claims claims = parseClaims(accessToken);
        User user = userRepository.findByEmail(claims.getSubject()).orElseThrow(NoUserExistsException::new);

        UserPrincipal principal = UserPrincipal.create(user);
        return new LoginAuthenticationToken(principal);
    }

    // Token 갱신에 사용할 정보를 얻기 위해 Claim 값(Access Token을 복호화한 값)을 구함
    public Claims parseClaims(String accessToken) {
        return Jwts.parserBuilder().setSigningKey(keyStream)
                .build().parseClaimsJws(accessToken).getBody();
    }

    public String getTokenType(String accessToken) {
        return Jwts.parserBuilder()
                .setSigningKey(keyStream)
                .build()
                .parseClaimsJws(accessToken)
                .getBody()
                .getOrDefault("type", ACCESS_TOKEN_NAME).toString();
    }
}
