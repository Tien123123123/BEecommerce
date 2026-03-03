package com.ecommerce.be.ecommercebe.repository;

import com.ecommerce.be.ecommercebe.model.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<CartEntity, Long> {
}
