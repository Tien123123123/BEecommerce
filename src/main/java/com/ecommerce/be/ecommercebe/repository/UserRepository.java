package com.ecommerce.be.ecommercebe.repository;

import com.ecommerce.be.ecommercebe.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);
}
