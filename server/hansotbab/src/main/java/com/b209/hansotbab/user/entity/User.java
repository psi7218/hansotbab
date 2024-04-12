package com.b209.hansotbab.user.entity;

import com.b209.hansotbab.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

    @Id @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name="uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID uuid;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = true)
    private String nickname;

    @Column(nullable = false)
    private RoleType roleType;

    @Column(nullable = true)
    private String profileImgUrl;

    @Column(nullable = true)
    private String phoneNumber;

    @Column(nullable = true)
    private String name;

    @Column(nullable = false)
    private boolean alarmApproval;

    @Column(nullable = false)
    private LocalDateTime lastAtkIssuedAt;

    @Builder
    public User(String email, String nickname, RoleType roleType, String profileImgUrl,
                String phoneNumber, String name, boolean alarmApproval) {
        this.email = email;
        this.nickname = nickname;
        this.roleType = roleType;
        this.profileImgUrl = profileImgUrl;
        this.phoneNumber = phoneNumber;
        this.name = name;
        this.alarmApproval = alarmApproval;
        this.lastAtkIssuedAt = LocalDateTime.now().minusDays(22);
    }

    public void modifyNickname(String newNickname) {
        this.nickname = newNickname;
    }

    public void updateDelete(){
        this.isDelete = true;
    }

    public void withdraw() {
        this.isDelete = true;
    }

    public void updateAtkIssuedAt() {
        this.lastAtkIssuedAt = LocalDateTime.now();
    }

    public void changeToAdmin() {
        this.roleType = RoleType.ADMIN;
    }
}
