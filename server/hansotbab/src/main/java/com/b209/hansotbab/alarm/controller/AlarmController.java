package com.b209.hansotbab.alarm.controller;

import com.b209.hansotbab.alarm.dto.request.AlarmRequestDTO;
import com.b209.hansotbab.alarm.dto.request.AlarmUserRequestDTO;
import com.b209.hansotbab.alarm.entity.FcmToken;
import com.b209.hansotbab.alarm.repository.FcmTokenRepository;
import com.b209.hansotbab.alarm.service.AlarmService;
import com.b209.hansotbab.user.entity.UserPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/alarm")
@RequiredArgsConstructor
public class AlarmController {

    private final AlarmService alarmService;

    /**
     * 실제 알림을 전송하는 controller
     *
     * @param alarmRequestDTO 알림 메시지와 타겟 유저
     * @return 알림 성공 여부
     */
    @PostMapping
    public String sendAlarmByToken(@RequestBody AlarmRequestDTO alarmRequestDTO) {
        return alarmService.sendAlarmByToken(alarmRequestDTO);
    }

    @PostMapping("/user")
    public ResponseEntity<?> userAlarm(@RequestBody AlarmUserRequestDTO alarmUserRequestDTO) {

        alarmService.createFcmToken(alarmUserRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

    /**
     * 냉장고에 음식이 기부되면 그 냉장고를 즐겨찾기한 유저에게 알림 주는 controller
     *
     * @param fridgeId
     * @return 상태
     */
    @PostMapping("/fridge/{fridgeId}")
    public ResponseEntity<?> sendAlarmByFridge(@PathVariable Long fridgeId) {
        if (fridgeId == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        alarmService.sendAlarmByFridge(fridgeId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("product/{productId}")
    public ResponseEntity<?> sendAlarmByProduct(@PathVariable Long productId) {
        if (productId == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        alarmService.sendAlarmByProduct(productId);
        return ResponseEntity.status(HttpStatus.OK).build();


    }
}

