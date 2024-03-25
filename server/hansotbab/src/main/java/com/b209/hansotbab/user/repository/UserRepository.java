package com.b209.hansotbab.user.repository;

import com.b209.hansotbab.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserIdAndIsDeleteIsFalse(Long userId);
}
