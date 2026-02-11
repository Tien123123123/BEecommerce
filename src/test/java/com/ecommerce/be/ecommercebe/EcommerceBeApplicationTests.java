package com.ecommerce.be.ecommercebe;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class EcommerceBeApplicationTests {

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url",
                () -> "jdbc:postgresql://127.0.0.1:5432/e-commerceDB?user=postgres&password=password");
        registry.add("spring.datasource.username", () -> "postgres");
        registry.add("spring.datasource.password", () -> "password");
        registry.add("spring.data.redis.host", () -> "localhost");
        registry.add("spring.data.redis.port", () -> "6379");
    }

    @Autowired
    private org.springframework.core.env.Environment env;

    @Test
    void contextLoads() {
        System.out.println("DEBUG: spring.datasource.url=" + env.getProperty("spring.datasource.url"));
        System.out.println("DEBUG: spring.datasource.username=" + env.getProperty("spring.datasource.username"));
        System.out.println("DEBUG: spring.datasource.password=" + env.getProperty("spring.datasource.password"));
    }

}
