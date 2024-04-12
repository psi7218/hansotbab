package com.b209.hansotbab.user.entity;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.util.UUID;

@Getter
@RedisHash
public class RefreshToken {

    @Id
    private String uuid;
    private String refreshToken;

    public RefreshToken(UUID uuid, String refreshToken) {
        this.uuid = uuid.toString();
        this.refreshToken = refreshToken;
    }
}
