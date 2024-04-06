package com.b209.hansotbab.user;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

public class WithMockMvcUserSecurityContextFactory implements WithSecurityContextFactory<WithMockMvcUser> {

    @Override
    public SecurityContext createSecurityContext(WithMockMvcUser annotation) {
        return null;
    }
}
