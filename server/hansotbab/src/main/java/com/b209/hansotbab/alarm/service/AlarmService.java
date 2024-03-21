package com.b209.hansotbab.alarm.service;

import com.b209.hansotbab.alarm.dto.request.AlarmRequestDTO;

public interface AlarmService {
    String sendAlarmByToken(AlarmRequestDTO alarmRequestDTO);
}
