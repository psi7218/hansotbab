package com.b209.hansotbab.user.controller;

import com.b209.hansotbab.user.dto.request.*;
import com.b209.hansotbab.user.dto.response.TokenDTO;
import com.b209.hansotbab.user.dto.response.UserProfileDTO;
import com.b209.hansotbab.user.dto.response.UserResponseDTO;
import com.b209.hansotbab.user.entity.UserPrincipal;
import com.b209.hansotbab.user.exception.*;
import com.b209.hansotbab.user.service.UserService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@Slf4j
@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/info/{uuid}")
    public ResponseEntity<UserResponseDTO> getUserInfo(@PathVariable String uuid) {
        return ResponseEntity.ok(userService.getUserInfo(uuid));
    }

    @PostMapping("/reissue/access")
    public ResponseEntity<String> newAccessToken(@RequestBody String refreshToken) {
        return ResponseEntity.ok(userService.reissueAccessToken(refreshToken));
    }

    @PostMapping("/reissue/refresh")
    public ResponseEntity<TokenDTO> newRefreshToken(@RequestBody TokenReissueRequestDTO tokenReissueRequest) {
        return ResponseEntity.ok(userService.reissueRefreshToken(
                    tokenReissueRequest.getUuid(), tokenReissueRequest.getAccessToken()));
    }

    @PostMapping("/register")
    public ResponseEntity<UserProfileDTO> register(@RequestBody RegisterRequestDTO registerRequest) {
        try {
            UserProfileDTO userProfile = userService.register(registerRequest);
            log.info("Register Success for " + registerRequest.getEmail());
            return ResponseEntity.ok(userProfile);

        } catch (RegisterFailedException e) {
            log.error("This user already exists : " + registerRequest.getEmail());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@RequestBody LoginRequestDTO loginRequest) {
        TokenDTO token = userService.login(loginRequest);
        log.info("Login Success for " + loginRequest.getUuid());
        log.info("Access Token issued : " + token.getAccessToken());

        return ResponseEntity.ok(token);
    }

    @PostMapping("/flutter-login")
    public ResponseEntity<String> flutterLogin(@RequestBody LoginRequestDTO loginRequest) {
        String token = userService.flutterLogin(loginRequest);
        log.info("Login Success for " + loginRequest.getUuid());
        log.info("Access Token issued : " + token);

        return ResponseEntity.ok(token);
    }

    @PostMapping("/admin")
    public ResponseEntity<Void> authorizeAdmin(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                            @RequestBody AdminReqestDTO adminRequest) {
        try {
            String uuid = userPrincipal.getUuid();
        } catch (NullPointerException e) {
            log.error("Unauthorized user requested Admin Role");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        userService.changeRoleToAdmin(userPrincipal, adminRequest);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("/info")
    public ResponseEntity<Void> changeNickname(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                            @RequestBody NicknameDTO newNickname) {
        // 바꿀 닉네임을 입력하지 않은 경우
        if(newNickname.getNewNickname().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        // 로그인하지 않은 사용자가 닉네임 변경을 요청한 경우
        if(userPrincipal.getUsername().isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // 현재 로그인한 사용자와 닉네임을 변경하려는 사용자 email이 다른 경우
        if(!userPrincipal.getEmail().equals(newNickname.getEmail())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        try {
            userService.changeNickname(newNickname.getEmail(), newNickname.getNewNickname());
            log.info("Nickname changed for " + userPrincipal.getUuid());
            return ResponseEntity.status(HttpStatus.OK).build();

        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping()
    public ResponseEntity<Void> withdraw(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        try {
            userService.withdraw(userPrincipal.getEmail());
            log.info("Withdraw Success for " + userPrincipal.getUuid());
            return ResponseEntity.ok().build();

        } catch (NoSuchElementException e) {
            // 로그인하지 않은 사용자가 회원 탈퇴를 요청한 경우
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
