package com.b209.hansotbab.user.repository;

import com.b209.hansotbab.user.entity.UserFcmToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserFcmTokenReposiotory extends JpaRepository<UserFcmToken, UUID> {
}
