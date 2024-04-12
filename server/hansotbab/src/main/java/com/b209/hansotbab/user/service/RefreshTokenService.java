package com.b209.hansotbab.user.service;

import com.b209.hansotbab.user.repository.TokenRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RefreshTokenService {

    private final TokenRepository tokenRepository;

    public RefreshTokenService(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    public void saveRefreshToken(String uuid, String refreshToken) {
        tokenRepository.saveRefreshToken(uuid, refreshToken);
    }
}
