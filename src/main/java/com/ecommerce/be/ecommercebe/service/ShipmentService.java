package com.ecommerce.be.ecommercebe.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class ShipmentService {
    private final Logger logger = LoggerFactory.getLogger(ShipmentService.class);
}
