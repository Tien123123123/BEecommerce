package com.ecommerce.be.ecommercebe.repository;

import com.ecommerce.be.ecommercebe.model.ShipmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShipmentRepository extends JpaRepository<ShipmentEntity, Long> {
}
