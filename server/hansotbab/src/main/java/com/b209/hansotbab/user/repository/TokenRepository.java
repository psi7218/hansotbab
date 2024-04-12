package com.b209.hansotbab.user.repository;

import com.b209.hansotbab.user.entity.RefreshToken;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class TokenRepository {

    // uuid, refreshToken 순서
    @Resource(name = "redisTemplate")
    private SetOperations<String, String> refreshTokens;

    public boolean isRefreshTokenExists(String uuid) {
        return Boolean.TRUE.equals(refreshTokens.getOperations().hasKey(uuid));
    }

    public String getRefreshToken(String uuid) {
        return refreshTokens.randomMember(uuid);
    }

    public void saveRefreshToken(String uuid, String refreshToken) {
        refreshTokens.add(uuid, refreshToken);
        refreshTokens.getOperations().expire(uuid, 14L, TimeUnit.DAYS);
    }

    public void deleteRefreshToken(String uuid) {
        if(Boolean.TRUE.equals(refreshTokens.getOperations().hasKey(uuid))) {
            refreshTokens.remove(uuid);
        }
    }
}
