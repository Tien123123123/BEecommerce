package com.ecommerce.be.ecommercebe.repository;

import com.ecommerce.be.ecommercebe.model.ShopEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.lang.ScopedValue;
import java.util.Optional;

public interface ShopRepository extends JpaRepository<ShopEntity, Long> {
    Optional<ShopEntity> findFirstBySeller_IdAndId(Long sellerId, Long id);
}
