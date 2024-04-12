package com.b209.hansotbab.fridge.entity;

import com.b209.hansotbab.global.entity.BaseTimeEntity;
import com.b209.hansotbab.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FridgeLike extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fridgeLikesId;

    @ManyToOne(fetch = LAZY )
    @JoinColumn(name = "fridge_id")
    private Fridge fridge;

    @ManyToOne(fetch = LAZY )
    @JoinColumn(name = "user_uuid")
    private User user;

    @Builder
    public FridgeLike(Long fridgeLikesId, Fridge fridge, User user) {
        this.fridgeLikesId = fridgeLikesId;
        this.fridge = fridge;
        this.user = user;
    }
}
