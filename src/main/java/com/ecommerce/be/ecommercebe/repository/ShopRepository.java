package com.ecommerce.be.ecommercebe.repository;

import com.ecommerce.be.ecommercebe.model.ShopEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopRepository extends JpaRepository<ShopEntity, Long> {
}
