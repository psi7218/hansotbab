package com.b209.hansotbab.alarm.dto.request;

import com.b209.hansotbab.alarm.entity.FcmToken;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AlarmUserRequestDTO {
    private String email;
    private String fcmToken;

    @Builder
    public AlarmUserRequestDTO(String email, String fcmToken){
        this.email = email;
        this.fcmToken = fcmToken;
    }

    public FcmToken toFcmToken(String email, String fcmToken){
        return FcmToken.builder()
                .email(email)
                .fcmToken(fcmToken)
                .build();
    }

}
