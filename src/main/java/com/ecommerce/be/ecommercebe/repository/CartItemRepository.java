package com.ecommerce.be.ecommercebe.repository;

import com.ecommerce.be.ecommercebe.model.CartItemEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItemEntity, Long> {

    @Query(nativeQuery = true, value = "select soft_delete from cart_items where id = :id")
    Boolean isSoftDeleted(@Param("id") Long id);

    @Query(nativeQuery = true, value = "select * from cart_items where id = :id limit 1 )")
    Optional<CartItemEntity> getItemIncludeDeleted(@Param("id") Long id);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "delete from cart_items where id in :ids")
    void hardDeleteAllByIds(@Param("ids") Iterable<Long> ids);
}
