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

//    @KafkaListener(topics = "user-events", groupId = "ecommerce-kafka")
//    public void testKafka(
//            UserResponse userDTO, // Đảm bảo UserResponse có @NoArgsConstructor
//            @Header(KafkaHeaders.RECEIVED_KEY) String user_id,
//            @Header(KafkaHeaders.RECEIVED_PARTITION) int partition,
//            @Header(KafkaHeaders.OFFSET) long offset
//    ) {
//        logger.info("Data received from kafka");
//        logger.info("Received user event - Key (userId): {}, Partition: {}, Offset: {}, UserId từ payload: {}",
//                user_id, partition, offset, userDTO.getId());
//        logger.info("User DTO: " + userDTO);
//
//        if (userDTO.getId() != null && !user_id.equals(String.valueOf(userDTO.getId()))) {
//            logger.warn("Key không khớp với id trong payload!");
//        }
//    }
}
