package com.ecommerce.be.ecommercebe.repository;

import com.ecommerce.be.ecommercebe.model.ShopEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Modifying;

import java.util.List;
import java.util.Optional;

public interface ShopRepository extends JpaRepository<ShopEntity, Long> {
    @Query(value = "SELECT soft_delete FROM shops WHERE seller_id = :shopId", nativeQuery = true)
    Boolean isSoftDeleted(@Param("shopId") Long shopId);

    @Query(value = "SELECT * FROM shops WHERE seller_id = :shopId LIMIT 1", nativeQuery = true)
    Optional<ShopEntity> findShopEntityById_IncludingDeleted(@Param("shopId") Long shop_id);

    Optional<ShopEntity> findFirstById(Long shopId);

    @Modifying
    @Query(value = "UPDATE shops SET soft_delete = false WHERE seller_id = :shopId", nativeQuery = true)
    void restoreById(@Param("shopId") Long shopId);
}
