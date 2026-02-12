package com.ecommerce.be.ecommercebe.repository;

import com.ecommerce.be.ecommercebe.model.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

    @Query(value = "select * from categories where id= :categoryId limit 1", nativeQuery = true)
    Optional<CategoryEntity> getCategoryIncludeDeleted(@Param("categoryId") Long category_id);

    @Query(value = "select soft_delete from categories where id= :categoryId", nativeQuery = true)
    Boolean isSoftDeleted(@Param("categoryId") Long category_id);

    @Modifying
    @Query(value = "UPDATE categories SET soft_delete = false WHERE id= :categoryId", nativeQuery = true)
    void restoreById(@Param("categoryId") Long category_id);
}
