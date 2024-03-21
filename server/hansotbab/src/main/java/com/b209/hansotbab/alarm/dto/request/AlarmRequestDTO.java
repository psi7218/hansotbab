package com.b209.hansotbab.alarm.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AlarmRequestDTO {

    private Long targetUserId;
    private String title;
    private String body;

    @Builder
    public AlarmRequestDTO(Long targetUserId, String title, String body){
        this.targetUserId = targetUserId;
        this.title = title;
        this.body = body;
    }

}
