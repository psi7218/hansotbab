package com.b209.hansotbab.user.dto.response;

import com.b209.hansotbab.user.dto.response.LikedFridgeDTO;
import com.b209.hansotbab.user.entity.RoleType;
import com.b209.hansotbab.user.entity.User;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Getter
public class UserProfileDTO implements Serializable {

    private final String uuid;
    private final String email;
    private final String nickname;
    private final String profileImage;
    private final String roleType;
    private final String phoneNumber;
    private final String name;
    private final boolean alarmApproval;
    private final List<LikedFridgeDTO> likedFridges;

    @Builder
    public UserProfileDTO(String uuid, String email, String nickname, String profileImage, String roleType,
                          String phoneNumber, String name, boolean alarmApproval, List<LikedFridgeDTO> likedFridges) {
        this.uuid = uuid;
        this.email = email;
        this.nickname = nickname;
        this.profileImage = profileImage;
        this.roleType = roleType;
        this.phoneNumber = phoneNumber;
        this.name = name;
        this.alarmApproval = alarmApproval;
        this.likedFridges = likedFridges;
    }
}
