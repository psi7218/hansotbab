package com.b209.hansotbab.alarm.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FcmToken {
    @Id
    private String email;

    @Column(nullable = false)
    private String fcmToken;

    @Builder
    public FcmToken(String email, String fcmToken) {
        this.email = email;
        this.fcmToken = fcmToken;
    }
}
