package com.ecommerce.be.ecommercebe.repository;

import com.ecommerce.be.ecommercebe.model.ProductVariantEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductVariantRepository extends JpaRepository<ProductVariantEntity, Long> {
}
