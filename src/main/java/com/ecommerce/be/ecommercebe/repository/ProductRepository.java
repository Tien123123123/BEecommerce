package com.ecommerce.be.ecommercebe.repository;

import com.ecommerce.be.ecommercebe.model.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
}
