package com.b209.hansotbab.alarm.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 실제 firebase를 통해 알림을 동작할 때 사용하는 DTO
 */
@Getter
@NoArgsConstructor
public class AlarmRequestDTO {
    private String targetUserEmail;
    private String title;
    private String body;


    @Builder
    public AlarmRequestDTO(String targetUserEmail, String title, String body){
        this.targetUserEmail = targetUserEmail;
        this.title = title;
        this.body = body;
    }
}
