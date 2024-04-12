package com.b209.hansotbab.user.service;

import com.b209.hansotbab.user.entity.User;
import com.b209.hansotbab.user.entity.UserPrincipal;
import com.b209.hansotbab.user.exception.NoUserExistsException;
import com.b209.hansotbab.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserPrincipalService implements UserDetailsService {

    private final UserRepository userRepository;

    public UserPrincipalService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserPrincipal loadUserByUsername(String uuid) throws UsernameNotFoundException {
        return userRepository.findById(UUID.fromString(uuid)).map(this::createUserPrincipal)
                .orElseThrow(NoUserExistsException::new);
    }

    private UserPrincipal createUserPrincipal(User user) {

        return UserPrincipal.create(user);
    }
}
