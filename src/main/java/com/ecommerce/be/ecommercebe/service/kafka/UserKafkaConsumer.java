package com.ecommerce.be.ecommercebe.service.kafka;

import com.ecommerce.be.ecommercebe.dto.response.UserResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Component
public class UserKafkaConsumer {
    private static final Logger logger = LoggerFactory.getLogger(UserKafkaConsumer.class);

    @KafkaListener(topics = "user-events", groupId = "ecommerce-group")
    public void consumeUserEvent(
            UserResponse userDTO,
            @Header(KafkaHeaders.RECEIVED_KEY) String userId,
            @Header(KafkaHeaders.RECEIVED_PARTITION) int partition,
            @Header(KafkaHeaders.OFFSET) long offset) {
        logger.info("[KAFKA_CONSUMER] Received event for User: {}", userDTO.getFullname());
        logger.info("[KAFKA_CONSUMER] Metadata - Topic: user-events, Key: {}, Partition: {}, Offset: {}", userId,
                partition, offset);

        // Giả sử đây là nơi bạn gọi Notification Service để gửi email chào mừng
        sendWelcomeEmail(userDTO);
    }

    private void sendWelcomeEmail(UserResponse user) {
        logger.info("[NOTIFICATION_SERVICE] Sending welcome email to: {}", user.getEmail());
        // Logic gửi email thực tế sẽ nằm ở đây
    }
}
