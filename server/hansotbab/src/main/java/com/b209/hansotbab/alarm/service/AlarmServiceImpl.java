package com.b209.hansotbab.alarm.service;

import com.b209.hansotbab.alarm.dto.request.AlarmRequestDTO;
import com.b209.hansotbab.alarm.dto.request.AlarmUserRequestDTO;
import com.b209.hansotbab.alarm.entity.FcmToken;
import com.b209.hansotbab.alarm.repository.FcmTokenRepository;
import com.b209.hansotbab.fridge.entity.Fridge;
import com.b209.hansotbab.fridge.entity.FridgeLike;
import com.b209.hansotbab.fridge.entity.Product;
import com.b209.hansotbab.fridge.repository.FridgeLikeRepository;
import com.b209.hansotbab.fridge.repository.FridgeRepository;
import com.b209.hansotbab.fridge.repository.ProductRepository;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Transactional
@RequiredArgsConstructor
@Service
public class AlarmServiceImpl implements AlarmService {

    private final FirebaseMessaging firebaseMessaging;
    private final FcmTokenRepository fcmTokenRepository;
    private final FridgeLikeRepository fridgeLikeRepository;
    private final FridgeRepository fridgeRepository;
    private final ProductRepository productRepository;

    /**
     * 실제 device token으로 알림을 보내는 메소드
     *
     * @param alarmRequestDTO 메시지제목, 내용, 받는 사람의 deviceToken
     * @return 알림성공여부에대한 메시지
     */
    @Override
    public String sendAlarmByToken(AlarmRequestDTO alarmRequestDTO) {

        FcmToken user = fcmTokenRepository.findById(alarmRequestDTO.getTargetUserEmail()).orElseThrow(() -> new IllegalArgumentException("가입하지 않은 회원입니다. "));


        if (user.getFcmToken() != null) {
            Notification notification = new Notification(alarmRequestDTO.getTitle(), alarmRequestDTO.getBody());
            Message messsage = Message.builder().setToken(user.getFcmToken()).setNotification(notification).build();
            try {
                firebaseMessaging.send(messsage);
                return "알림성공 targetUserEmail=" + alarmRequestDTO.getTargetUserEmail();
            } catch (FirebaseMessagingException e) {
                e.printStackTrace();
                return "알림 실패 targetUserEmail = " + alarmRequestDTO.getTargetUserEmail();
            }

        } else {
            return "해당 유저 미존재 + targetUserEmail=" + alarmRequestDTO.getTargetUserEmail();
        }
    }

    @Override
    public void createFcmToken(AlarmUserRequestDTO alarmUserRequestDTO) {
        String email = alarmUserRequestDTO.getEmail();
        String fcmToken = alarmUserRequestDTO.getFcmToken();
        Optional<FcmToken> user = fcmTokenRepository.findById(email);
        if (user.isPresent()) {
            new IllegalArgumentException("이미 FCM 토큰이 등록되었습니다.");
        } else {
            FcmToken fcmtokenUser = alarmUserRequestDTO.toFcmToken(email, fcmToken);
            fcmTokenRepository.save(fcmtokenUser);
        }

    }

    @Override
    public void sendAlarmByFridge(Long fridgeId) {
        Fridge fridge = fridgeRepository.findById(fridgeId).orElseThrow(()-> new IllegalArgumentException("없는 냉장고 입니다."));
        List<FridgeLike> fridgeLikeList = fridgeLikeRepository.findAllByFridge(fridge);
        Integer fridgeNumber = fridge.getFridgeNumber();
        String title = "한솥밥 음식등록알림";
        String body = String.format("%d호점에 음식이 등록되었습니다.", fridgeNumber);

        for (FridgeLike fridgeLike : fridgeLikeList) {
            String email = fridgeLike.getUser().getEmail();
            AlarmRequestDTO alarmRequestDTO = AlarmRequestDTO.builder().targetUserEmail(email).title(title).body(body).build();
            sendAlarmByToken(alarmRequestDTO);
        }
    }

    @Override
    public void sendAlarmByProduct(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new IllegalArgumentException("없는 음식입니다."));
        String email = product.getUser().getEmail();
        String title = "한솥밥 감사인사알림";
        String body = "감사인사가 등록되었습니다.";

        AlarmRequestDTO alarmRequestDTO = AlarmRequestDTO.builder().targetUserEmail(email).title(title).body(body).build();
        sendAlarmByToken(alarmRequestDTO);
    }
}
