package com.b209.hansotbab.user.service;

import com.b209.hansotbab.user.dto.request.AdminReqestDTO;
import com.b209.hansotbab.user.dto.request.LoginRequestDTO;
import com.b209.hansotbab.user.dto.request.RegisterRequestDTO;
import com.b209.hansotbab.user.dto.response.*;
import com.b209.hansotbab.user.entity.*;
import com.b209.hansotbab.user.exception.*;
import com.b209.hansotbab.user.jwt.LoginAuthenticationToken;
import com.b209.hansotbab.user.jwt.TokenProvider;
import com.b209.hansotbab.user.repository.*;

import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDateTime;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.UUID;

@Slf4j
@Service
public class UserService {

    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;
    private final RefreshTokenService tokenService;
    private final TokenRepository tokenRepository;

    public UserService(UserRepository userRepository, TokenProvider tokenProvider,
                       RefreshTokenService tokenService, TokenRepository tokenRepository) {
        this.userRepository = userRepository;
        this.tokenProvider = tokenProvider;
        this.tokenService = tokenService;
        this.tokenRepository = tokenRepository;
    }

    @Transactional
    public UserResponseDTO getUserInfo(String uuid) {
        User user = userRepository.findByUuidAndIsDeleteFalse(UUID.fromString(uuid))
                    .orElseThrow(NoUserExistsException::new);

        return UserResponseDTO.builder().email(user.getEmail()).nickname(user.getNickname())
                .role(user.getRoleType() == RoleType.USER ? "사용자" : "관리자").profileImgUrl(user.getProfileImgUrl())
                .phoneNumber(user.getPhoneNumber()).name(user.getName()).alarmApproval(user.isAlarmApproval()).build();
    }

    @Transactional
    public UserProfileDTO register(RegisterRequestDTO registerRequest) throws RegisterFailedException {
        if (userRepository.findByEmail(registerRequest.getEmail()).isPresent()) {
            throw new RegisterFailedException();
        }

        User user = User.builder().email(registerRequest.getEmail()).nickname(registerRequest.getNickname())
                .roleType(RoleType.USER).profileImgUrl(registerRequest.getProfileImgUrl()).phoneNumber("")
                .name("").alarmApproval(false).build();

        user = userRepository.save(user);

        return UserProfileDTO.builder().uuid(user.getUuid().toString()).email(user.getEmail())
                .nickname(user.getNickname()).profileImage(user.getProfileImgUrl())
                .roleType(user.getRoleType().getCode()).phoneNumber(user.getPhoneNumber())
                .name(user.getName()).alarmApproval(user.isAlarmApproval())
                .likedFridges(new ArrayList<LikedFridgeDTO>()).build();
    }

    @Transactional
    public String flutterLogin(LoginRequestDTO loginRequest) {
        User user = userRepository.findByUuidAndIsDeleteFalse(UUID.fromString(loginRequest.getUuid()))
                    .orElseThrow(NoUserExistsException::new);

        if(user.getLastAtkIssuedAt().isAfter(ChronoLocalDateTime.from(LocalDateTime.now().minusDays(20)))) {
            throw new AlreadyAuthenticatedException();
        }

        // Access Token 발급
        UserPrincipal principal = UserPrincipal.create(user);
        String atk = tokenProvider.createFlutterToken(principal);
        user.updateAtkIssuedAt();

        // Security Context Setting
        // 기존에 등록된 Security Context가 있는 상황에서 Access Token을 재발급 하는 경우 context clear 먼저 수행
        SecurityContextHolder.clearContext();
        SecurityContext context = SecurityContextHolder.createEmptyContext();

        Authentication authentication = new LoginAuthenticationToken(principal);
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);

        return atk;
    }

    @Transactional
    public TokenDTO login(LoginRequestDTO loginRequest) {
        User user = userRepository.findByUuidAndIsDeleteFalse(UUID.fromString(loginRequest.getUuid()))
                    .orElseThrow(NoUserExistsException::new);

        if(user.getLastAtkIssuedAt().isAfter(ChronoLocalDateTime.from(LocalDateTime.now().minusMinutes(120)))) {
            throw new AlreadyAuthenticatedException();
        }

        String atk = issueAccessToken(user);
        // Refresh Token이 유효하다면 그대로 return
        if(tokenRepository.isRefreshTokenExists(user.getUuid().toString())) {
            String uuid = user.getUuid().toString();

            log.info("Refresh Token exists : " + tokenRepository.getRefreshToken(uuid));
            return new TokenDTO(uuid, atk, tokenRepository.getRefreshToken(uuid));
        }

        String rtk = issueRefreshToken();
        return new TokenDTO(user.getUuid().toString(), atk, rtk);
    }

    @Transactional
    protected String issueAccessToken(User user) {
        // Access Token 발급
        UserPrincipal principal = UserPrincipal.create(user);
        String atk = tokenProvider.createAccessToken(principal);
        user.updateAtkIssuedAt();

        // Security Context Setting
        // 기존에 등록된 Security Context가 있는 상황에서 Access Token을 재발급 하는 경우 context clear 먼저 수행
        SecurityContextHolder.clearContext();
        SecurityContext context = SecurityContextHolder.createEmptyContext();

        Authentication authentication = new LoginAuthenticationToken(principal);
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);

        return atk;
    }

    @Transactional
    protected String issueRefreshToken() {
        UserPrincipal principal = (UserPrincipal) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        String rtk = tokenProvider.createRefreshToken(principal);
        // Redis 저장
        tokenRepository.deleteRefreshToken(principal.getUuid());
        tokenService.saveRefreshToken(principal.getUuid(), rtk);

        return rtk;
    }

    @Transactional
    public void changeNickname(String email, String newNickname) {
        User user = userRepository.findByEmail(email).orElseThrow();
        user.modifyNickname(newNickname);
    }

    @Transactional
    public String reissueAccessToken(String refreshToken) {
        // refresh token 유효 -> access token 재발급
        if(tokenProvider.isValidToken(refreshToken)) {
            String email = tokenProvider.parseClaims(refreshToken).getSubject();
            User user = userRepository.findByEmail(email).orElseThrow(NoUserExistsException::new);

            tokenRepository.isRefreshTokenExists(user.getUuid().toString());
            String newAccessToken = issueAccessToken(user);

            log.info("Access Token reissued for user " + user.getUuid() + " : " + newAccessToken);
            return newAccessToken;
        }

        // refresh token 만료 (Redis에서 검색 불가능) -> 에러 메시지 리턴
        else {
            throw new InvalidTokenException();
        }
    }

    @Transactional
    public TokenDTO reissueRefreshToken(String uuid, String accessToken) {
        // access token 복호화 -> email을 제외한 내용이 정의한 규칙과 맞는지 확인
        Claims claims = tokenProvider.parseClaims(accessToken);
        if(!claims.getIssuer().equals("Hansotbab") || !claims.get("type").equals("access_token")) {
            throw new InvalidTokenException();
        }

        // RequestBody로 받은 uuid로 user 검증
        User user = userRepository.findByUuidAndIsDeleteFalse(UUID.fromString(uuid))
                .orElseThrow(NoUserExistsException::new);

        // Refresh Token 재발급을 요청한 유저와 Access Token을 발급받은 유저가 다르면 Unauthorized Exception
        if(!user.getEmail().equals(claims.getSubject())) {
            throw new UnauthorizedException();
        }

        String atk = issueAccessToken(user);
        String rtk = issueRefreshToken();

        return new TokenDTO(uuid, atk, rtk);
    }

    @Transactional
    public void logout(UserPrincipal principal) {
        // Todo : Redis에서 Refresh Token Blacklist에 추가
        SecurityContextHolder.clearContext();
    }

    @Transactional
    public void changeRoleToAdmin(UserPrincipal principal, AdminReqestDTO adminRequest) {
        if(!principal.getUuid().equals(adminRequest.getUuid()) || !principal.getRoleType().equals(RoleType.ADMIN)) {
            log.error("User " + principal.getUuid() + " requested Admin Role for another user " + adminRequest.getUuid());
            throw new NotAuthenticactedException();
        }

        User user = userRepository.findByUuidAndIsDeleteFalse(UUID.fromString(principal.getUuid()))
                .orElseThrow(NoUserExistsException::new);

        user.changeToAdmin();
    }

    @Transactional
    public void withdraw(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(NoSuchElementException::new);
        user.withdraw();
    }
}
