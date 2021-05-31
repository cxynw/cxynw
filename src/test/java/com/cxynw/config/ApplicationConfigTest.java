package com.cxynw.config;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class ApplicationConfigTest {

    @Autowired
    ApplicationConfig config;

    @Test
    void getName() {
        log.info(config.getName());
        assertNotNull(config.getName());
    }

    @Test
    void getKeywords() {
        log.info(config.getKeywords());
        assertNotNull(config.getKeywords());
    }

    @Test
    void getDescription() {
        log.info(config.getDescription());
        assertNotNull(config.getDescription());
    }
}