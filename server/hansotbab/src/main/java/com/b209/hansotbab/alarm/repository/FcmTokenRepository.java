package com.b209.hansotbab.alarm.repository;

import com.b209.hansotbab.alarm.entity.FcmToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FcmTokenRepository extends JpaRepository<FcmToken, String> {
}
