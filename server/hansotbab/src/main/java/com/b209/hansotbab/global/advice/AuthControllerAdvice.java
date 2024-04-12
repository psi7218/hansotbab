package com.b209.hansotbab.global.advice;

import com.b209.hansotbab.user.exception.*;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AuthControllerAdvice {

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<String> expiredJwtException() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Expired Token");
    }

    @ExceptionHandler(AlreadyAuthenticatedException.class)
    public ResponseEntity<String> alreadyAuthenticatedException() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Already Authenticated User");
    }

    @ExceptionHandler(NotAuthenticactedException.class)
    public ResponseEntity<String> notAuthenticactedException() {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Not Authenticated User");
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<String> invalidTokenException() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Token");
    }

    @ExceptionHandler(RegisterFailedException.class)
    public ResponseEntity<String> registerFailedException() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Already registered user");
    }

    @ExceptionHandler(NoUserExistsException.class)
    public ResponseEntity<String> noUserExistsException() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Not Registered User");
    }
}
