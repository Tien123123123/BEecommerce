package com.ecommerce.be.ecommercebe.repository;

import com.ecommerce.be.ecommercebe.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    @Query(value = "SELECT soft_delete FROM users WHERE id = :id", nativeQuery = true)
    Boolean isSoftDeleted(@Param("id") Long id);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query(value = "UPDATE users SET soft_delete = true WHERE id = :id", nativeQuery = true)
    void softDeleteById(@Param("id") Long id);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query(value = "UPDATE users SET soft_delete = false WHERE id = :id", nativeQuery = true)
    void restoreById(@Param("id") Long id);
}
