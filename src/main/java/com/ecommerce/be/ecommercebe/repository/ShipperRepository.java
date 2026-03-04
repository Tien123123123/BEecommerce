package com.ecommerce.be.ecommercebe.repository;

import com.ecommerce.be.ecommercebe.model.ShipperEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ShipperRepository extends JpaRepository<ShipperEntity, Long> {

    @Query(nativeQuery = true,
            value = "select * from shippers where id = :userId limit 1"
    )
    ShipperEntity getShipperIncludeDeleted(@Param("userId") Long userId);

    @Query(nativeQuery = true,
            value = "select soft_delete from shippers where id = :userId"
    )
    Boolean isSoftDeleted(@Param("userId") Long userId);

    @Modifying
    @Query(nativeQuery = true,
            value = "update shippers set soft_delete = false where id = :userId"
    )
    void restoreById(@Param("userId") Long userId);
}
