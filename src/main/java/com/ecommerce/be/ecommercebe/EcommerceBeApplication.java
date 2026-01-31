package com.ecommerce.be.ecommercebe;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableCaching
@EnableKafka
public class EcommerceBeApplication {

    public static void main(String[] args) {
        SpringApplication.run(EcommerceBeApplication.class, args);
    }

    @Bean
    NewTopic notification(){
        // Topic + partitions + brokers
        return new NewTopic("notification", 2, (short) 1);
    }

    @Bean
    NewTopic statistic(){
        // Topic + partitions + brokers
        return new NewTopic("statistic", 1, (short) 1);
    }

}
