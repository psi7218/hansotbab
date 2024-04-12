package com.b209.hansotbab.user.util;

import com.b209.hansotbab.user.entity.UserPrincipal;
import com.b209.hansotbab.user.exception.NotAuthenticactedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthUtils {

    public static String getCurrentUserId() {
        final UserPrincipal principal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(principal == null || principal.getUsername() == null) {
            throw new NotAuthenticactedException();
        }

        return principal.getUsername();
    }
}
