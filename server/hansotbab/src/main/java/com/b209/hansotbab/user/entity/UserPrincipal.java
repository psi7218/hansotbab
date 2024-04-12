package com.b209.hansotbab.user.entity;

import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.time.LocalDateTime;
import java.util.*;

@Getter
public class UserPrincipal implements UserDetails {

    private final String uuid;
    private final String email;
    private final String nickname;
    private final RoleType roleType;
    private final String profileImgUrl;
    private final String phoneNumber;
    private final String name;
    private final boolean alarmApproval;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;
    private final Collection<GrantedAuthority> authorities;

    @Builder
    public UserPrincipal(String uuid, String email, String nickname, RoleType roleType, String profileImgUrl,
                         String phoneNumber, String name, boolean alarmApproval, LocalDateTime createdAt,
                         LocalDateTime modifiedAt, Collection<GrantedAuthority> authorities) {
        this.uuid = uuid;
        this.email = email;
        this.nickname = nickname;
        this.roleType = roleType;
        this.profileImgUrl = profileImgUrl;
        this.phoneNumber = phoneNumber;
        this.name = name;
        this.alarmApproval = alarmApproval;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.authorities = authorities;
    }

    public static UserPrincipal create(User user) {
        List<GrantedAuthority> authorities = Collections.singletonList(
                new SimpleGrantedAuthority(user.getRoleType().getCode()));

        return UserPrincipal.builder().uuid(user.getUuid().toString()).email(user.getEmail()).nickname(user.getNickname())
                .roleType(user.getRoleType()).profileImgUrl(user.getProfileImgUrl()).phoneNumber(user.getPhoneNumber())
                        .name(user.getName()).alarmApproval(user.isAlarmApproval()).createdAt(user.getCreatedDate())
                        .modifiedAt(user.getModifiedDate()).authorities(authorities).build();
    }

    public static UserPrincipal create(User user, Map<String, Object> attributes) {

        return UserPrincipal.create(user);
    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        return uuid;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }
}
