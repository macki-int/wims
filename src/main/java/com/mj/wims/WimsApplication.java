package com.mj.wims;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WimsApplication {
    private static final Logger LOGGER = LogManager.getLogger(WimsApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(WimsApplication.class, args);

        LOGGER.info("Start app Wims");
    }
}
