package com.ecommerce.be.ecommercebe.repository;

import com.ecommerce.be.ecommercebe.model.SellerEntity;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface SellerRepository extends JpaRepository<SellerEntity, Long> {
    boolean existsByUserEntityId(Long id);

    Optional<SellerEntity> findByUserEntity_Id(Long userEntityId);

    @Query(value = "SELECT * FROM sellers WHERE user_id = :userId LIMIT 1", nativeQuery = true)
    Optional<SellerEntity> findByUserIdIncludingDeleted(@Param("userId") Long userId);

    @Query(value = "SELECT soft_delete FROM sellers WHERE user_id = :userId", nativeQuery = true)
    Boolean isSoftDeleted(@Param("userId") Long userId);

    @Modifying
    @Query(value = "UPDATE sellers SET soft_delete = false WHERE id = :id", nativeQuery = true)
    void restoreById(@Param("id") Long id);
}
