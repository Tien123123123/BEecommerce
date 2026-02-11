package com.ecommerce.be.ecommercebe.repository;

import com.ecommerce.be.ecommercebe.model.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    @Query(value = "select soft_delete from products where id= :productId", nativeQuery = true)
    Boolean isDeleted(@Param("productId") Long product_id);

    @Query(value = "select * from products where id=: productId limit 1", nativeQuery = true)
    Optional<ProductEntity> getProductEntitiesIncludeDeleted(@Param("productId") Long product_id);
}
