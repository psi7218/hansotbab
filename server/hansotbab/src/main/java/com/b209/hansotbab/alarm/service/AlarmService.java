package com.b209.hansotbab.alarm.service;

import com.b209.hansotbab.alarm.dto.request.AlarmRequestDTO;
import com.b209.hansotbab.alarm.dto.request.AlarmUserRequestDTO;

public interface AlarmService {

    String sendAlarmByToken(AlarmRequestDTO alarmRequestDTO);

    void createFcmToken(AlarmUserRequestDTO alarmUserRequestDTO);

    void sendAlarmByFridge(Long fridgeId);

    void sendAlarmByProduct(Long productId);
}
