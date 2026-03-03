package com.ecommerce.be.ecommercebe.repository;

import com.ecommerce.be.ecommercebe.model.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<AddressEntity, Long> {
}
