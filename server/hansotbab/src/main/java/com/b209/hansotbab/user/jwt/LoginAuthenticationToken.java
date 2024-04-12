package com.b209.hansotbab.user.jwt;

import com.b209.hansotbab.user.entity.UserPrincipal;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class LoginAuthenticationToken extends AbstractAuthenticationToken {

    private final UserPrincipal principal;

    public LoginAuthenticationToken(UserPrincipal principal) {
        super(principal.getAuthorities());

        if (principal.getAuthorities() != null) {
            this.principal = principal;
            this.setAuthenticated(true);
        } else {
            throw new IllegalArgumentException("Cannot pass null or empty values to constructor");
        }
    }

    @Override
    public Object getCredentials() {
        return "";
    }

    @Override
    public UserPrincipal getPrincipal() {
        return this.principal;
    }
}
