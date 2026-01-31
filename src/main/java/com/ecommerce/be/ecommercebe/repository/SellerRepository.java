package com.ecommerce.be.ecommercebe.repository;

import com.ecommerce.be.ecommercebe.model.SellerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SellerRepository extends JpaRepository<SellerEntity, Long> {
    boolean existsByUserEntityId(Long id);

    Optional<SellerEntity> findByUserEntity_Id(Long userEntityId);
}
