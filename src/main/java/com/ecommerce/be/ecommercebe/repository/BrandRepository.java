package com.ecommerce.be.ecommercebe.repository;

import com.ecommerce.be.ecommercebe.model.BrandEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BrandRepository extends JpaRepository<BrandEntity, Long> {

    @Query(value = "select * from brands where id= :brandId limit 1", nativeQuery = true)
    Optional<BrandEntity> getBrandIncludeDeleted(@Param("brandId") Long brand_id);

    @Query(value = "select soft_delete from brands where id= :brandId", nativeQuery = true)
    Boolean isSoftDeleted(@Param("brandId") Long brand_id);

    @Modifying
    @Query(value = "UPDATE brands SET soft_delete = false WHERE id= :brandId", nativeQuery = true)
    void restoreById(@Param("brandId") Long brand_id);
}
