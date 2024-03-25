package com.b209.hansotbab.user.repository;

import com.b209.hansotbab.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByUuidAndIsDeleteFalse(UUID uuid);

    Optional<User> findByEmail(String email);
}
